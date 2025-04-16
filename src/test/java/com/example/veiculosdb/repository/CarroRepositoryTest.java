package com.example.veiculosdb.repository;

import com.example.veiculosdb.domain.v1.model.Carro;
import com.example.veiculosdb.domain.v1.ports.out.CarroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarroRepositoryTest {

    @Autowired
    private CarroRepository carroRepository;

    @Test
    void deveSalvarERecuperarCarroPorPlaca() {
        Carro carro = new Carro();
        carro.setMarca("Fiat");
        carro.setModelo("Uno");
        carro.setAnoFabricacao(2012);
        carro.setPlaca("ZZZ-9999");
        carro.setTipo("Hatch");
        carro.setValorMercado(new BigDecimal("15000"));

        carroRepository.save(carro);

        Optional<Carro> encontrado = carroRepository.findByPlaca("ZZZ-9999");

        assertTrue(encontrado.isPresent());
        assertEquals("Fiat", encontrado.get().getMarca());
    }
}
