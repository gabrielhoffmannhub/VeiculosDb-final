package com.example.veiculosdb.controller;

import com.example.veiculosdb.adapters.in.CarroController;
import com.example.veiculosdb.application.CarroService;
import com.example.veiculosdb.domain.v1.model.Carro;
import com.example.veiculosdb.dto.v1.CarroRequestDTO;
import com.example.veiculosdb.dto.v1.CarroResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarroControllerTest {

    @Mock
    private CarroService carroService;

    @InjectMocks
    private CarroController carroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarCarro() {

        CarroRequestDTO request = new CarroRequestDTO();
        request.setMarca("Toyota");
        request.setModelo("Corolla");
        request.setAnoFabricacao(2020);
        request.setPlaca("ABC1234");
        request.setTipo("Sedan");
        request.setValorMercado(new BigDecimal("85000"));

        Carro carroSalvo = new Carro();
        carroSalvo.setId(1L);
        carroSalvo.setMarca(request.getMarca());
        carroSalvo.setModelo(request.getModelo());
        carroSalvo.setAnoFabricacao(request.getAnoFabricacao());
        carroSalvo.setPlaca(request.getPlaca());
        carroSalvo.setTipo(request.getTipo());
        carroSalvo.setValorMercado(request.getValorMercado());

        when(carroService.salvar(any(Carro.class))).thenReturn(carroSalvo);


        ResponseEntity<CarroResponseDTO> response = carroController.criarCarro(request);


        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Toyota", response.getBody().getMarca());
        assertEquals("Corolla", response.getBody().getModelo());
        assertEquals("ABC1234", response.getBody().getPlaca());

        verify(carroService, times(1)).salvar(any(Carro.class));
    }
}
