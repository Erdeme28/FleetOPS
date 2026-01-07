package com.fleet.gateway.repository;

import com.fleet.gateway.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    // Putem adăuga metode speciale aici dacă e nevoie,
    // dar momentan e suficient standardul (save, findAll, etc.)
}