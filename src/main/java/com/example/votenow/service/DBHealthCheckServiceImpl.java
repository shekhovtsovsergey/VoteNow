package com.example.votenow.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class DBHealthCheckServiceImpl implements DBHealthCheckService{

    private final DataSource dataSource;

    @Override
    public Health getDBHealth() {
        try {
            Connection connection = dataSource.getConnection();
            connection.close();
        } catch (SQLException e) {
            return Health.down().withDetail("DB Connection", "Disconnected").build();
        }
        return Health.up().withDetail("DB Connection", "Connected").build();
    }
}
