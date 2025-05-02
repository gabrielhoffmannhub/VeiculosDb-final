package com.example.veiculosdb.application.servicetest;

import com.example.veiculosdb.application.service.CarroService;
import com.example.veiculosdb.domain.model.Carro;
import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import com.example.veiculosdb.exception.CarroNotFoundException;
import com.example.veiculosdb.ports.output.CarroRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CarroServiceTest {

    @Mock
    private CarroRepositoryPort carroRepositoryPort;

    @InjectMocks
    private CarroService carroService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarCarro() {
        CarroRequestDTO request = new CarroRequestDTO("Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        Carro carro = new Carro(null, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        Carro carroSalvo = new Carro(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));

        when(carroRepositoryPort.salvar(any(Carro.class))).thenReturn(carroSalvo);

        CarroResponseDTO response = carroService.salvar(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getMarca()).isEqualTo("Fiat");
        verify(carroRepositoryPort, times(1)).salvar(any(Carro.class));
    }

    @Test
    void deveListarTodosOsCarros() {
        Carro carro1 = new Carro(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        Carro carro2 = new Carro(2L, "Volkswagen", "Gol", 2019, "XYZ5678", "Hatch", BigDecimal.valueOf(25000));

        when(carroRepositoryPort.listarTodos()).thenReturn(List.of(carro1, carro2));

        List<CarroResponseDTO> carros = carroService.listarTodos();

        assertThat(carros).hasSize(2);
        assertThat(carros.get(0).getModelo()).isEqualTo("Uno");
        assertThat(carros.get(1).getModelo()).isEqualTo("Gol");
        verify(carroRepositoryPort, times(1)).listarTodos();
    }

    @Test
    void deveBuscarCarroPorPlaca() {
        Carro carro = new Carro(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));

        when(carroRepositoryPort.buscarPorPlaca("ABC1234")).thenReturn(Optional.of(carro));

        CarroResponseDTO response = carroService.buscarPorPlaca("ABC1234");

        assertThat(response).isNotNull();
        assertThat(response.getPlaca()).isEqualTo("ABC1234");
        verify(carroRepositoryPort, times(1)).buscarPorPlaca("ABC1234");
    }

    @Test
    void deveLancarExcecaoQuandoCarroNaoEncontrado() {
        when(carroRepositoryPort.buscarPorPlaca("XYZ9999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carroService.buscarPorPlaca("XYZ9999"))
                .isInstanceOf(CarroNotFoundException.class)
                .hasMessage("Carro com placa XYZ9999 não encontrado");

        verify(carroRepositoryPort, times(1)).buscarPorPlaca("XYZ9999");
    }

    @Test
    void deveAtualizarCarro() {
        Carro carro = new Carro(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        Carro carroAtualizado = new Carro(1L, "Fiat", "Palio", 2021, "ABC1234", "Hatch", BigDecimal.valueOf(35000));
        CarroRequestDTO updateRequest = new CarroRequestDTO("Fiat", "Palio", 2021, "ABC1234", "Hatch", BigDecimal.valueOf(35000));

        when(carroRepositoryPort.buscarPorPlaca("ABC1234")).thenReturn(Optional.of(carro));
        when(carroRepositoryPort.salvar(any(Carro.class))).thenReturn(carroAtualizado);

        CarroResponseDTO response = carroService.atualizarPorPlaca("ABC1234", updateRequest);

        assertThat(response).isNotNull();
        assertThat(response.getModelo()).isEqualTo("Palio");
        assertThat(response.getAnoFabricacao()).isEqualTo(2021);
        verify(carroRepositoryPort, times(1)).buscarPorPlaca("ABC1234");
        verify(carroRepositoryPort, times(1)).salvar(any(Carro.class));
    }

    @Test
    void deveDeletarCarro() {
        Carro carro = new Carro(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));

        when(carroRepositoryPort.buscarPorPlaca("ABC1234")).thenReturn(Optional.of(carro));
        doNothing().when(carroRepositoryPort).deletar(carro);

        carroService.deletarPorPlaca("ABC1234");

        verify(carroRepositoryPort, times(1)).buscarPorPlaca("ABC1234");
        verify(carroRepositoryPort, times(1)).deletar(carro);
    }

    @Test
    void deveCalcularDepreciacao() {
        Carro carro = new Carro(1L, "Fiat", "Uno", 2010, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        when(carroRepositoryPort.buscarPorPlaca("ABC1234")).thenReturn(Optional.of(carro));

        BigDecimal depreciacao = carroService.calcularDepreciacao("ABC1234");

        assertThat(depreciacao).isNotNull();
        assertThat(depreciacao).isEqualTo(BigDecimal.valueOf(13898.74));
        verify(carroRepositoryPort, times(1)).buscarPorPlaca("ABC1234");
    }
}