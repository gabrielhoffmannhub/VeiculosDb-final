package com.example.veiculosdb.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CarroTest {

    @Test
    public void deveCalcularDepreciacaoCorretamente() {
        Carro carro = new Carro(1L, "Volkswagen", "Fusca", 1980, "ABC1234", "Hatch", new BigDecimal("15000.00"));

        BigDecimal depreciacao = carro.calcularDepreciacao();

        assertThat(depreciacao).isNotNull();
        assertThat(depreciacao).isGreaterThan(BigDecimal.ZERO);
    }
}