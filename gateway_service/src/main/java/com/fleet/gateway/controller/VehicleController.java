package com.fleet.gateway.controller;

import com.fleet.gateway.entity.Vehicle;
import com.fleet.gateway.repository.VehicleRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*") // Permite accesul de pe Frontend (port 3000)
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Operation(summary = "Listeaza toate vehiculele", description = "Returneaza o lista cu toata flota si pozitiile curente.")
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Operation(summary = "Adauga un vehicul nou", description = "Creeaza un vehicul in baza de date.")
    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}