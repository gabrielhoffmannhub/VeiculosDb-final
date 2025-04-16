package com.example.veiculosdb.service;

import com.example.veiculosdb.domain.v1.model.Carro;
import com.example.veiculosdb.domain.v1.ports.out.CarroRepository;
import com.example.veiculosdb.application.CarroService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarroServiceTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private CarroService carroService;

    public CarroServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveBuscarCarroPorPlaca() {
        Carro carro = new Carro();
        carro.setPlaca("ABC-1234");

        when(carroRepository.findByPlaca("ABC-1234")).thenReturn(Optional.of(carro));

        Optional<Carro> resultado = carroService.buscarPorPlaca("ABC-1234");

        assertTrue(resultado.isPresent());
        assertEquals("ABC-1234", resultado.get().getPlaca());
    }
}
