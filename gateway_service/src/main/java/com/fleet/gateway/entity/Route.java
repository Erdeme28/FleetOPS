package com.fleet.gateway.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Distanța totală (ex: 15.4 km)
    private Double distanceKm;

    // Timpul estimat (ex: 20 min)
    private Double estimatedTimeMinutes;

    // Punctele rutei salvate ca un text lung (JSON sau CSV: "45.5,25.5;46.0,26.0")
    // Folosim @Column(columnDefinition = "TEXT") pentru a permite string-uri foarte lungi
    @Column(columnDefinition = "TEXT")
    private String waypoints;

    // O rută aparține unei singure comenzi
    @OneToOne
    private Order order;
}