package com.ECO.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECO.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByAvailability(boolean b);
    // Benutzerdefinierte Methoden für die Fahrzeugverwaltung

    List<Vehicle> findByMake(String make);

    List<Vehicle> findByModel(String model);
}
