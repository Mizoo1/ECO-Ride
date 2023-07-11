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
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationConfirmationEmailService emailService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationConfirmationEmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.emailService = emailService;
    }

    public void processReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.RESERVED);
        Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        reservation.setReservierungsDatum(currentDate);
        reservationRepository.save(reservation);


        emailService.sendReservationConfirmationEmail(reservation.getAppUser().getEmail(), ReservationStatus.RESERVED, reservation.getAppUser(), reservation);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Could not find reservation " + reservationId));

        reservation.setStatus(ReservationStatus.CANCELLED);
        Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        reservation.setReservierungsDatum(currentDate);
        reservationRepository.save(reservation);


        emailService.sendReservationConfirmationEmail(reservation.getAppUser().getEmail(), ReservationStatus.CANCELLED, reservation.getAppUser(), reservation);
    }

    public void completeReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Could not find reservation " + reservationId));
        reservation.setStatus(ReservationStatus.COMPLETED);
        Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        reservation.setReservierungsDatum(currentDate);
        reservationRepository.save(reservation);


        emailService.sendReservationConfirmationEmail(reservation.getAppUser().getEmail(), ReservationStatus.COMPLETED, reservation.getAppUser(), reservation);
    }

    @Scheduled(fixedDelay = 60000) // Führt die Methode alle 60 Sekunden aus
    public void checkMissedReservations() {
        System.out.println("Checking missed reservations...");

        List<Reservation> reservations = reservationRepository.findAllByStatus(ReservationStatus.RESERVED);
        System.out.println("Number of reserved reservations: " + reservations.size());

        for (Reservation reservation : reservations) {
            System.out.println("Processing reservation: " + reservation.getId());

            String reservationDate = reservation.getDatum(); // Assuming the reservation date is stored as a String in format "yyyy-MM-dd"
            System.out.println("Reservation date: " + reservationDate);

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedReservationDate = LocalDate.parse(reservationDate, formatter);

            if (parsedReservationDate.isBefore(currentDate) && !reservation.getStatus().equals(ReservationStatus.COMPLETED)) {
                System.out.println("Reservation missed: " + reservation.getId());

                // Mark the reservation as missed
                missReservation(reservation.getId());

                System.out.println("Reservation marked as missed: " + reservation.getId());
            }
        }
    }


    public void missReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Could not find reservation " + reservationId));

        // Mark the reservation as missed
        reservation.setStatus(ReservationStatus.MISSED);
        Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        reservation.setReservierungsDatum(currentDate);
        reservationRepository.save(reservation);
        emailService.sendReservationConfirmationEmail(reservation.getAppUser().getEmail(), ReservationStatus.MISSED, reservation.getAppUser(), reservation);
    }
}
