package com.ECO.repository;

import com.ECO.model.Booking;
import com.ECO.model.User;
import com.ECO.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    List<Booking> findByVehicle(Vehicle vehicle);

    Optional<Booking> findById(Long bookingId);
}
