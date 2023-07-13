package com.ECO.controller;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.Reservation;
import com.ECO.login_System.appuser.ReservationStatus;
import com.ECO.repository.ReservationRepository;
import com.ECO.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView showReservationForm(Model model,Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("reservierung");
        String userName = userDetails.getUserName();
        modelAndView.addObject("userName", userName);
        return modelAndView;
    }

    public ModelAndView getReservierung(Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("reservierung");
        String userName = userDetails.getUserName();
        modelAndView.addObject("userName", userName);
        return modelAndView;
    }
    @PostMapping
    public String submitReservationForm(@ModelAttribute("reservation") Reservation reservation,
                                        @RequestParam(name = "panne", required = false) boolean panne) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        reservation.setAppUser(currentUser);

        reservation.setBarrierefrei(panne);
        reservation.setStatus(ReservationStatus.RESERVED);

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
    @ModelAttribute("cancelledReservations")
    public List<Reservation> getCancelledReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        return reservationRepository.findAllByAppUserAndStatus(currentUser, ReservationStatus.CANCELLED);
    }
    @ModelAttribute("missedReservations")
    public List<Reservation> getMissedReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        return reservationRepository.findAllByAppUserAndStatus(currentUser, ReservationStatus.MISSED);
    }

}
