package com.fleet.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; // Import obligatoriu
import org.springframework.web.client.RestTemplate; // Import obligatoriu

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    // AICI E LOCUL CORECT - O singura data in toata aplicatia
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}