package com.ECO.service;

import com.ECO.login_System.appuser.Reservation;
import com.ECO.login_System.appuser.ReservationStatus;
import com.ECO.login_System.registration.ReservationConfirmationEmailService;
import com.ECO.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService
{
    private final ReservationRepository reservationRepository;
    private final ReservationConfirmationEmailService emailService;
    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              ReservationConfirmationEmailService emailService)
    {
        this.reservationRepository = reservationRepository;
        this.emailService = emailService;
    }
    // Verarbeitet eine Reservierung und sendet eine Bestätigungs-E-Mail
    public void processReservation(Reservation reservation)
    {
        reservation.setStatus(ReservationStatus.RESERVED);
        Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        reservation.setReservierungsDatum(currentDate);
        reservationRepository.save(reservation);
        emailService.sendReservationConfirmationEmail(reservation.getAppUser().
                getEmail(), ReservationStatus.RESERVED,
                reservation.getAppUser(), reservation);
    }
    // Storniert eine Reservierung und sendet eine Stornierungs-E-Mail
    public void cancelReservation(Long reservationId)
    {
        // Suche nach der Reservierung mit der angegebenen ID in der Datenbank
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new NoSuchElementException("Could not find reservation " + reservationId));
        reservation.setStatus(ReservationStatus.CANCELLED);
        // Setze das aktuelle Datum und die Uhrzeit als Reservierungsdatum
        Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        reservation.setReservierungsDatum(currentDate);
        reservationRepository.save(reservation);
        // Sende eine Bestätigungs-E-Mail für die stornierte Reservierung an den App-Benutzer
        emailService.sendReservationConfirmationEmail(reservation.getAppUser().
                getEmail(), ReservationStatus.CANCELLED,
                reservation.getAppUser(), reservation);
    }
    // Überprüft verpasste Reservierungen in einem festgelegten Intervall
    @Scheduled(fixedDelay = 60000)
    public void checkMissedReservations()
    {
        System.out.println("Checking missed reservations...");
        // Abruf aller Reservierungen mit dem Status "RESERVED" aus dem Reservierungsrepository
        List<Reservation> reservations = reservationRepository.findAllByStatus(ReservationStatus.RESERVED);
        System.out.println("Number of reserved reservations: " + reservations.size());
        // Durchlaufen aller Reservierungen in der Liste
        for (Reservation reservation : reservations)
        {
            System.out.println("Processing reservation: " + reservation.getId());
            String reservationDate = reservation.getDatum();
            System.out.println("Reservation date: " + reservationDate);
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedReservationDate = LocalDate.parse(reservationDate, formatter);
            // Prüfen, ob das Reservierungsdatum vor dem aktuellen Datum liegt
            // und der Status der Reservierung nicht "COMPLETED" ist
            if (parsedReservationDate.isBefore(currentDate) && !reservation.
                    getStatus().equals(ReservationStatus.COMPLETED))
            {
                System.out.println("Reservation missed: " + reservation.getId());
                missReservation(reservation.getId());
                System.out.println("Reservation marked as missed: " + reservation.getId());
            }
        }
    }
    // Markiert eine verpasste Reservierung und sendet eine Benachrichtigungs-E-Mail
    public void missReservation(Long reservationId)
    {
        /** Abrufen der Reservierung mit der gegebenen ID aus dem Reservierungsrepository.
         * Falls keine Reservierung mit dieser ID gefunden werden kann,
         * wird eine NoSuchElementException geworfen
         */
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new NoSuchElementException("Could not find reservation " + reservationId));
        reservation.setStatus(ReservationStatus.MISSED);
        Date currentDate = Date.from(LocalDateTime.now().
                atZone(ZoneId.systemDefault()).toInstant());
        reservation.setReservierungsDatum(currentDate);
        reservationRepository.save(reservation);
        emailService.sendReservationConfirmationEmail(reservation.getAppUser().getEmail(),
                ReservationStatus.MISSED, reservation.getAppUser(), reservation);
    }
}
