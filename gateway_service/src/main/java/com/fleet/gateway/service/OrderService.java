package com.fleet.gateway.service;

import com.fleet.gateway.entity.Order;
import com.fleet.gateway.entity.Route;
import com.fleet.gateway.metrics.OrderMetrics;
import com.fleet.gateway.repository.OrderRepository;
import com.fleet.gateway.repository.RouteRepository;
import com.fleet.gateway.websocket.WebSocketPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RouteRepository routeRepository;
    private final RouteEngineClient routeEngineClient;
    private final ObjectMapper mapper;
    private final WebSocketPublisher publisher;
    private final OrderMetrics metrics;

    public OrderService(
            OrderRepository orderRepository,
            RouteRepository routeRepository,
            RouteEngineClient routeEngineClient,
            ObjectMapper mapper,
            WebSocketPublisher publisher,
            OrderMetrics metrics
    ) {
        this.orderRepository = orderRepository;
        this.routeRepository = routeRepository;
        this.routeEngineClient = routeEngineClient;
        this.mapper = mapper;
        this.publisher = publisher;
        this.metrics = metrics;
    }

    @Transactional
    public Order createOrder(String start, String end) throws Exception {

        Order order = new Order();
        order.setStartLocation(start);
        order.setEndLocation(end);
        order.setStatus("CREATED");
        order = orderRepository.save(order);

        String routeJson = routeEngineClient.calculateRoute(start, end);
        JsonNode node = mapper.readTree(routeJson);

        Route route = new Route();
        route.setOrder(order);
        route.setDistanceKm(node.get("distanceKm").asDouble());
        route.setEstimatedTimeMinutes(node.get("estimatedTimeMinutes").asDouble());
        route.setWaypoints(node.get("waypoints").asText());
        routeRepository.save(route);

        order.setStatus("ROUTE_CALCULATED");
        order = orderRepository.save(order);

        metrics.incrementOrders();
        publisher.send("/topic/orders/" + order.getId(), order);

        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
