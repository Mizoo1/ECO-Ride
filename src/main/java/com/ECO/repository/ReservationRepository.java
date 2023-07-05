package com.ECO.repository;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByAppUser(AppUser appUser);
}
