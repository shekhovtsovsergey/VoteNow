package com.example.votenow.service;

import org.springframework.boot.actuate.health.Health;

public interface DBHealthCheckService {
    public Health getDBHealth();

}
