package com.fleet.gateway.controller;

import com.fleet.gateway.entity.Order;
import com.fleet.gateway.repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Operation(summary = "Creeaza o comanda noua", description = "Clientul trimite punctul A si B.")
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // Setam statusul initial
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    @Operation(summary = "Listeaza comenzile", description = "Adminul vede toate comenzile.")
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}