package com.ECO.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECO.model.Booking;
import com.ECO.model.User;
import com.ECO.model.Vehicle;
import com.ECO.repository.BookingRepository;
import com.ECO.repository.UserRepository;
import com.ECO.repository.VehicleRepository;
import com.ECO.repository.userRepository;

@Service
public class BookingService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public void createBooking(Long vehicleId, User userId, LocalDateTime startTime, LocalDateTime endTime) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID: " + vehicleId));

        if (!isVehicleAvailable(vehicle, startTime, endTime)) {
            throw new IllegalStateException("Vehicle is not available for the specified dates");
        }

        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        Booking booking = new Booking();
        booking.setVehicle(vehicle);
        booking.setUser(userId);
        booking.setStarTime(startTime);
        booking.setEndTime(endTime);

        vehicle.getBookings().add(booking);

        vehicleRepository.save(vehicle);
        bookingRepository.save(booking);
    }

    private boolean isVehicleAvailable(Vehicle vehicle, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> bookings = vehicle.getBookings();
        for (Booking booking : bookings) {
            if (startTime.isBefore(booking.getEndTime()) && endTime.isAfter(booking.getStarTime())) {
                return false;
            }
        }
        return true;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(User userId) {
        Vehicle user = UserRepository
    public List<Booking> getBookingsByUser(User userId) {
        User user = UserRepository.findById(userId)
                .oElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getBookingsByVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID: " + vehicleId));
        return bookingRepository.findByVehicle(vehicle);
    }

    public Vehicle getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));
    }

    public void cancelBooking(Long bookingId) {
        Vehicle booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));

        Vehicle vehicle = booking.getVehicle();
        List<Booking> bookings = vehicle.getBookings();
        bookings.removeIf(b -> b.getId().equals(bookingId));

        vehicleRepository.save(vehicle);
        bookingRepository.delete(booking);
    }

}
