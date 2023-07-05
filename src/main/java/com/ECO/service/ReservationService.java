package com.ECO.service;

import com.ECO.login_System.appuser.Reservation;
import com.ECO.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void processReservation(Reservation reservation) {
        // Fügen Sie hier die Logik zur Verarbeitung der Reservierung hinzu
        reservationRepository.save(reservation);
    }

    public void cancelReservation(Long reservationId) {
        // Fügen Sie hier die Logik zur Stornierung der Reservierung hinzu
        reservationRepository.deleteById(reservationId);
    }

    // Weitere Methoden für die Geschäftslogik der Reservierung können hier hinzugefügt werden
}
