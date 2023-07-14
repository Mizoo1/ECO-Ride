package com.ECO.repository;

import com.ECO.login_System.appuser.AppUser;
import com.ECO.login_System.appuser.Reservation;
import com.ECO.login_System.appuser.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>
{
    List<Reservation> findAllByAppUser(AppUser appUser);
    List<Reservation> findAllByAppUserAndStatus(AppUser appUser, ReservationStatus status);
    List<Reservation> findAllByAppUserAndStatusNot(AppUser appUser, ReservationStatus status);
    List<Reservation> findAllByStatus(ReservationStatus status);
    List<Reservation> findByAppUser(AppUser appUser);
    @Query("SELECT a.stadt, COUNT(a) FROM AppUser a GROUP BY a.stadt")
    List<Object[]> countLocations();
    @Query("SELECT a.stadt, a.geburtsort, a.payMethod, COUNT(a) FROM AppUser a GROUP BY a.stadt, a.geburtsort, a.payMethod")
    List<Object[]> countLocationPaymentMethod();
    @Query("SELECT r.appUser.payMethod, r.reservierungsDatum, COUNT(r) " +
            "FROM Reservation r " +
            "GROUP BY r.appUser.payMethod, r.reservierungsDatum " +
            "ORDER BY r.appUser.payMethod, r.reservierungsDatum")
    List<Object[]> countReservationsByPaymentMethodAndDate();
    @Query("SELECT r.appUser.payMethod, HOUR(r.reservierungsDatum), COUNT(r) " +
            "FROM Reservation r " +
            "GROUP BY r.appUser.payMethod, HOUR(r.reservierungsDatum) " +
            "ORDER BY r.appUser.payMethod, HOUR(r.reservierungsDatum)")
    List<Object[]> countReservationsByPaymentMethodAndTime();
}




