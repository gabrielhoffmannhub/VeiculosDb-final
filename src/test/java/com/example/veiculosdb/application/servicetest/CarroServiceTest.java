package com.example.veiculosdb.application.servicetest;

import com.example.veiculosdb.application.service.CarroService;
import com.example.veiculosdb.domain.model.Carro;
import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import com.example.veiculosdb.ports.output.CarroRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
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

        when(carroRepositoryPort.salvar(carro)).thenReturn(carroSalvo);

        CarroResponseDTO response = carroService.salvar(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getMarca()).isEqualTo("Fiat");
        verify(carroRepositoryPort, times(1)).salvar(carro);
    }

    @Test
    void deveAtualizarCarro() {
        Carro carro = new Carro(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        Carro carroAtualizado = new Carro(1L, "Fiat", "Palio", 2021, "ABC1234", "Hatch", BigDecimal.valueOf(35000));
        CarroRequestDTO updateRequest = new CarroRequestDTO("Fiat", "Palio", 2021, "ABC1234", "Hatch", BigDecimal.valueOf(35000));

        when(carroRepositoryPort.buscarPorPlaca("ABC1234")).thenReturn(Optional.of(carro));
        when(carroRepositoryPort.salvar(carroAtualizado)).thenReturn(carroAtualizado);

        Optional<CarroResponseDTO> response = carroService.atualizarPorPlaca("ABC1234", updateRequest);

        assertThat(response).isPresent();
        assertThat(response.get().getModelo()).isEqualTo("Palio");
        assertThat(response.get().getAnoFabricacao()).isEqualTo(2021);
        verify(carroRepositoryPort, times(1)).buscarPorPlaca("ABC1234");
        verify(carroRepositoryPort, times(1)).salvar(carroAtualizado);
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