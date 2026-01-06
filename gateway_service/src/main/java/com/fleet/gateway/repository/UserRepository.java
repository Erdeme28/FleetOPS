package com.fleet.gateway.repository;

import com.fleet.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Aceasta metoda magica ajuta Spring Security sa gaseasca userul dupa nume
    Optional<User> findByUsername(String username);
}