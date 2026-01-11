package com.fleet.gateway.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double distanceKm;

    private Double estimatedTimeMinutes;

    @Column(columnDefinition = "TEXT")
    private String waypoints;

    @OneToOne
    private Order order;
}