package com.fleet.gateway.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // "user" e cuvânt rezervat în Postgres, deci folosim "users"
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Aici va sta hash-ul (ex: $2a$10$Dk/...) nu parola text!

    private String role; // ADMIN, DRIVER, CLIENT
}