package com.example.veiculosdbtest.config;

import com.example.veiculosdb.ports.input.CarroServicePort;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestSecurityConfig {
    @Bean
    @Primary
    public CarroServicePort carroServicePort() {
        return Mockito.mock(CarroServicePort.class);
    }
}