package com.example.veiculosdbtest.adapters.output;

import com.example.veiculosdb.adapters.output.persistence.CarroRepository;
import com.example.veiculosdb.domain.model.Carro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CarroRepositoryTest {

    private CarroRepository carroRepository;

    @BeforeEach
    void setUp() {
        carroRepository = new CarroRepository();
    }

    @Test
    void testSaveCarro() {
        Carro carro = new Carro(null, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        Carro savedCarro = carroRepository.save(carro);
        assertNotNull(savedCarro.getId());
        assertEquals("Fiat", savedCarro.getMarca());
        assertEquals("Uno", savedCarro.getModelo());
    }

    @Test
    void testFindAllCarros() {
        carroRepository.save(new Carro(null, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000)));
        carroRepository.save(new Carro(null, "Ford", "Fiesta", 2021, "XYZ5678", "Hatch", BigDecimal.valueOf(35000)));

        List<Carro> carros = carroRepository.findAll();
        assertEquals(2, carros.size());
    }

    @Test
    void testFindByPlaca() {
        carroRepository.save(new Carro(null, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000)));

        Optional<Carro> carroOptional = carroRepository.findByPlaca("ABC1234");
        assertTrue(carroOptional.isPresent());
        assertEquals("Fiat", carroOptional.get().getMarca());
    }

    @Test
    void testDeleteCarro() {
        Carro carro = new Carro(null, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        carroRepository.save(carro);

        carroRepository.delete(carro);

        Optional<Carro> carroOptional = carroRepository.findByPlaca("ABC1234");
        assertFalse(carroOptional.isPresent());
    }
}
