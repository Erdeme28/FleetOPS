package com.fleet.gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RouteEngineClient {

    private final RestTemplate restTemplate;

    @Value("${route-service.url}")
    private String routeServiceUrl;

    public RouteEngineClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String calculateRoute(String start, String end) {
        String url = routeServiceUrl +
                "/calculate?start=" + start + "&end=" + end;

        return restTemplate.getForObject(url, String.class);
    }
}
