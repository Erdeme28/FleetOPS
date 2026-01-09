package com.fleet.gateway.websocket;

import com.fleet.gateway.entity.Vehicle;
import com.fleet.gateway.repository.VehicleRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class WebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;
    private final VehicleRepository vehicleRepository;
    private final Random random = new Random();

    public WebSocketPublisher(SimpMessagingTemplate messagingTemplate,
                              VehicleRepository vehicleRepository) {
        this.messagingTemplate = messagingTemplate;
        this.vehicleRepository = vehicleRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void publishVehiclePositions() {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        vehicles.forEach(v -> {
            v.setLatitude(45.60 + random.nextDouble() * 0.01);
            v.setLongitude(25.60 + random.nextDouble() * 0.01);
        });

        vehicleRepository.saveAll(vehicles);

        messagingTemplate.convertAndSend("/topic/vehicles", vehicles);
    }
}
