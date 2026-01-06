package com.fleet.gateway.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startLocation;
    private String endLocation;

    private String status; // Ex: PENDING, IN_PROGRESS, COMPLETED

    // Putem lega comanda de un vehicul (optional pentru moment)
    // @ManyToOne
    // private Vehicle assignedVehicle;
}