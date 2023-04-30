package com.example.votenow.healthcheck;

import com.example.votenow.service.DBHealthCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DBConnectionHealthCheck implements HealthIndicator {

    private final DBHealthCheckService dbHealthCheckService;

    @Override
    public Health health() {
        return dbHealthCheckService.getDBHealth();
    }
}
