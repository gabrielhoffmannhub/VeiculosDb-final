package com.example.veiculosdbtest.application.servicetest;

import com.example.veiculosdb.application.service.CarroService;
import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import com.example.veiculosdb.domain.model.Carro;
import com.example.veiculosdb.exception.CarroNotFoundException;
import com.example.veiculosdb.ports.output.CarroRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarroServiceTest {

    @InjectMocks
    private CarroService carroService;

    @Mock
    private CarroRepositoryPort carroRepositoryPort;

    private Carro carro;
    private CarroRequestDTO carroRequestDTO;
    private CarroResponseDTO carroResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carro = new Carro(1L, "Marca X", "Modelo Y", 2015, "ABC1234", "Sedan", new BigDecimal("20000"));
        carroRequestDTO = new CarroRequestDTO("Marca X", "Modelo Y", 2015, "ABC1234", "Sedan", new BigDecimal("20000"));
        carroResponseDTO = new CarroResponseDTO(carro);
    }

    @Test
    void salvarCarroTest() {
        when(carroRepositoryPort.save(any(Carro.class))).thenReturn(carro);

        CarroResponseDTO result = carroService.salvar(carroRequestDTO);

        assertNotNull(result);
        assertEquals(carro.getId(), result.getId());
        assertEquals(carro.getMarca(), result.getMarca());
        verify(carroRepositoryPort, times(1)).save(any(Carro.class));
    }

    @Test
    void listarTodosCarrosTest() {
        when(carroRepositoryPort.findAll()).thenReturn(Arrays.asList(carro));

        var result = carroService.listarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carro.getMarca(), result.get(0).getMarca());
        verify(carroRepositoryPort, times(1)).findAll();
    }

    @Test
    void buscarCarroPorPlacaTest() {
        when(carroRepositoryPort.findByPlaca("ABC1234")).thenReturn(Optional.of(carro));

        CarroResponseDTO result = carroService.buscarPorPlaca("ABC1234");

        assertNotNull(result);
        assertEquals("ABC1234", result.getPlaca());
        verify(carroRepositoryPort, times(1)).findByPlaca("ABC1234"); // Verifica a chamada de findByPlaca
    }

    @Test
    void buscarCarroPorPlacaNaoEncontradoTest() {
        when(carroRepositoryPort.findByPlaca("XYZ5678")).thenReturn(Optional.empty());

        assertThrows(CarroNotFoundException.class, () -> carroService.buscarPorPlaca("XYZ5678"));
        verify(carroRepositoryPort, times(1)).findByPlaca("XYZ5678"); // Verifica a chamada de findByPlaca
    }

    @Test
    void atualizarCarroTest() {
        CarroRequestDTO updateDTO = new CarroRequestDTO("Marca Z", "Modelo X", 2016, "ABC1234", "SUV", new BigDecimal("25000"));
        when(carroRepositoryPort.findByPlaca("ABC1234")).thenReturn(Optional.of(carro));
        when(carroRepositoryPort.save(any(Carro.class))).thenReturn(carro);

        CarroResponseDTO result = carroService.atualizarPorPlaca("ABC1234", updateDTO);

        assertNotNull(result);
        assertEquals("Marca Z", result.getMarca());
        assertEquals("Modelo X", result.getModelo());
        verify(carroRepositoryPort, times(1)).findByPlaca("ABC1234"); // Verifica a chamada de findByPlaca
        verify(carroRepositoryPort, times(1)).save(any(Carro.class)); // Verifica a chamada de save
    }

    @Test
    void deletarCarroTest() {
        when(carroRepositoryPort.findByPlaca("ABC1234")).thenReturn(Optional.of(carro));

        carroService.deletarPorPlaca("ABC1234");

        verify(carroRepositoryPort, times(1)).delete(any(Carro.class)); // Verifica a chamada de delete
    }

    @Test
    void calcularDepreciacaoTest() {
        Carro carro = new Carro(null, "Marca", "Modelo", 2020, "ABC1234", "SUV", new BigDecimal("15000"));
        when(carroRepositoryPort.findByPlaca("ABC1234")).thenReturn(Optional.of(carro));

        BigDecimal resultado = carroService.calcularDepreciacao("ABC1234");

        BigDecimal valorEsperado = new BigDecimal("11606.71");

        assertEquals(valorEsperado, resultado.setScale(2, BigDecimal.ROUND_HALF_UP));

        verify(carroRepositoryPort, times(1)).findByPlaca("ABC1234");
    }
}
