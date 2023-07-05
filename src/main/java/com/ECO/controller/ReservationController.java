package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.Reservation;
import com.ECO.repository.ReservationRepository;
import com.ECO.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservierung")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public String showReservationForm(Model model) {
        model.addAttribute("reservierung ", new Reservation());
        return "reservierung";
    }

    @PostMapping
    public String submitReservationForm(@ModelAttribute("reservation") Reservation reservation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        reservation.setAppUser(currentUser);

        reservationService.processReservation(reservation);

        return "redirect:/reservierung/confirmation";
    }
    @ModelAttribute("reservations")
    public List<Reservation> getReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        return reservationRepository.findAllByAppUser(currentUser);
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage() {
        return "reservierung_confirmation";
    }
    @PostMapping("/cancelReservation")
    public String cancelReservation(@RequestParam Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return "redirect:/reservierung";
    }


}
