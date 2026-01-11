package com.fleet.gateway.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OrderMetrics {

    private final Counter ordersCreatedCounter;

    public OrderMetrics(MeterRegistry meterRegistry) {
        this.ordersCreatedCounter = Counter.builder("orders.created.total")
                .description("Total number of orders created")
                .register(meterRegistry);
    }

    public void incrementOrders() {
        ordersCreatedCounter.increment();
    }
}
