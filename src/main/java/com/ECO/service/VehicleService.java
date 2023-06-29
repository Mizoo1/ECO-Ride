package com.ECO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECO.model.Vehicle;
import com.ECO.repository.VehicleRepository;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public void updateVehicleLocation(Long vehicleId, double latitude, double longitude) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID: " + vehicleId));

        vehicle.setLatitude(latitude);
        vehicle.setLongitude(longitude);

        vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getVehiclesNearLocation(double latitude, double longitude, double radiusInKm) {

        return vehicleRepository.findAll();

    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID: " + vehicleId));
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void updateVehicle(Long vehicleId, Vehicle updatedVehicle) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID: " + vehicleId));

        vehicle.setMake(updatedVehicle.getMake());
        vehicle.setModel(updatedVehicle.getModel());
        vehicle.setYear(updatedVehicle.getYear());
        // Update other properties as needed

        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID: " + vehicleId));

        vehicleRepository.delete(vehicle);
    }

}
