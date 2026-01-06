package com.fleet.gateway.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data // Lombok genereaza automat Getters, Setters, toString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String licensePlate; // Ex: BV-10-XYZ

    private String status; // Ex: FREE, BUSY

    // Coordonate GPS curente
    private Double latitude;
    private Double longitude;
}