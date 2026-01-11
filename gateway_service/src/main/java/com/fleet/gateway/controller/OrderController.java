package com.fleet.gateway.controller;

import com.fleet.gateway.entity.Order;
import com.fleet.gateway.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create an order and calculate the route using the C++ microservice")
    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) throws Exception {
        return orderService.createOrder(
                request.startLocation(),
                request.endLocation()
        );
    }

    @Operation(summary = "List all orders")
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    public record CreateOrderRequest(
            String startLocation,
            String endLocation
    ) {}
}
