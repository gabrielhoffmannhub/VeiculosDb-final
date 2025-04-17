package com.example.veiculosdb.service;

import com.example.veiculosdb.application.CarroService;
import com.example.veiculosdb.domain.v1.model.Carro;
import com.example.veiculosdb.domain.v1.ports.out.CarroRepository;
import com.example.veiculosdb.dto.v1.CarroRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarroServiceTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private CarroService carroService;

    private Carro carro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carro = new Carro();
        carro.setId(1L);
        carro.setMarca("Toyota");
        carro.setModelo("Corolla");
        carro.setAnoFabricacao(2020);
        carro.setPlaca("ABC1234");
        carro.setTipo("Sedan");
        carro.setValorMercado(new BigDecimal("100000"));
    }

    @Test
    void deveSalvarCarro() {
        when(carroRepository.save(carro)).thenReturn(carro);

        Carro salvo = carroService.salvar(carro);

        assertThat(salvo).isEqualTo(carro);
        verify(carroRepository, times(1)).save(carro);
    }

    @Test
    void deveBuscarPorPlaca() {
        when(carroRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(carro));

        Optional<Carro> encontrado = carroService.buscarPorPlaca("ABC1234");

        assertThat(encontrado).isPresent().contains(carro);
    }

    @Test
    void deveAtualizarCarroPorPlaca() {
        CarroRequestDTO dto = new CarroRequestDTO();
        dto.setMarca("Honda");
        dto.setModelo("Civic");
        dto.setAnoFabricacao(2019);
        dto.setTipo("Sedan");
        dto.setValorMercado(new BigDecimal("90000"));

        when(carroRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(carro));
        when(carroRepository.save(any(Carro.class))).thenReturn(carro);

        Optional<Carro> atualizado = carroService.atualizarPorPlaca("ABC1234", dto);

        assertThat(atualizado).isPresent();
        assertThat(atualizado.get().getMarca()).isEqualTo("Honda");
    }

    @Test
    void deveDeletarCarroPorPlaca() {
        when(carroRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(carro));
        doNothing().when(carroRepository).delete(carro);

        boolean deletado = carroService.deletarPorPlaca("ABC1234");

        assertThat(deletado).isTrue();
        verify(carroRepository).delete(carro);
    }

    @Test
    void deveRetornarFalseSeCarroNaoExistirAoDeletar() {
        when(carroRepository.findByPlaca("ZZZ9999")).thenReturn(Optional.empty());

        boolean resultado = carroService.deletarPorPlaca("ZZZ9999");

        assertThat(resultado).isFalse();
    }

    @Test
    void deveCalcularDepreciacaoCorretamente() {
        carro.setAnoFabricacao(LocalDate.now().getYear() - 2);
        carro.setValorMercado(new BigDecimal("100000"));

        BigDecimal depreciado = carroService.calcularDepreciacao(carro);

        assertThat(depreciado).isLessThan(new BigDecimal("100000"));
        assertThat(depreciado).isEqualByComparingTo("90250.00");

    }

    @Test
    void deveListarCarrosComFiltro() {
        Page<Carro> pageMock = new PageImpl<>(List.of(carro));
        when(carroRepository.buscarPorMarcaTipo("Toyota", "Sedan", PageRequest.of(0, 10))).thenReturn(pageMock);

        Page<Carro> resultado = carroService.buscarComFiltro("Toyota", "Sedan", 0, 10);

        assertThat(resultado.getContent()).hasSize(1).contains(carro);
    }
}
