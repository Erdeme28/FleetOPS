package com.fleet.gateway.controller;

import com.fleet.gateway.GatewayApplication;
import com.fleet.gateway.entity.Order;
import com.fleet.gateway.entity.Route;
import com.fleet.gateway.repository.OrderRepository;
import com.fleet.gateway.repository.RouteRepository; // Va trebui creat!
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderRepository orderRepository;
    private final RouteRepository routeRepository; // Repository pentru Rute
    private final RestTemplate restTemplate; // Clientul HTTP pentru a suna la C++

    public OrderController(OrderRepository orderRepository, RouteRepository routeRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.routeRepository = routeRepository;
        this.restTemplate = restTemplate;
    }

    @Operation(summary = "Creeaza comanda + Calculeaza ruta (C++)")
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // 1. Salvam comanda initiala
        order.setStatus("PROCESSING");
        Order savedOrder = orderRepository.save(order);

        // 2. ORCHESTRARE: Apelam serviciul C++
        // Atentie: Folosim numele containerului "route-service" definit in docker-compose
        String cPlusPlusUrl = "http://route-service:8081/calculate?start=" + order.getStartLocation() + "&end=" + order.getEndLocation();

        try {
            // Facem request-ul
            ResponseEntity<String> response = restTemplate.getForEntity(cPlusPlusUrl, String.class);

            // 3. Parsam JSON-ul primit de la C++
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // 4. Cream si salvam Ruta in baza de date
            Route route = new Route();
            route.setDistanceKm(root.path("distanceKm").asDouble());
            route.setEstimatedTimeMinutes(root.path("estimatedTimeMinutes").asDouble());
            route.setWaypoints(root.path("waypoints").asText());
            route.setOrder(savedOrder); // Legam ruta de comanda

            routeRepository.save(route); // PERSISTENTA COMPLETA

            // Actualizam statusul comenzii
            savedOrder.setStatus("ROUTED");
            orderRepository.save(savedOrder);

        } catch (Exception e) {
            e.printStackTrace();
            savedOrder.setStatus("ERROR_ROUTING");
            orderRepository.save(savedOrder);
        }

        return savedOrder;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
