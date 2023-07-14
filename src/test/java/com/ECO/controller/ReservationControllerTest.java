package com.ECO.controller;

import antlr.collections.List;
import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.Reservation;
import com.ECO.login_System.appuser.ReservationStatus;
import com.ECO.repository.ReservationRepository;
import com.ECO.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    public void testSubmitReservationForm()
    {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(ReservationStatus.RESERVED);

        AppUser appUser = new AppUser();
        appUser.setUserName("TestUser");
        appUser.setPassword("TestPassword");

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(appUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String result = reservationController.submitReservationForm(reservation, false);
        assertEquals("redirect:/reservierung/confirmation", result);
        verify(reservationService, times(1)).processReservation(any(Reservation.class));
    }
    @Test
    public void testCancelReservation()
    {
        Long reservationId = 1L;
        String result = reservationController.cancelReservation(reservationId);
        verify(reservationService, times(1)).cancelReservation(reservationId);
        assertEquals("redirect:/reservierung", result);
    }

}
