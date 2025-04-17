package com.example.veiculosdb.repository;

import com.example.veiculosdb.adapters.out.persistence.CarroRepositoryAdapter;
import com.example.veiculosdb.adapters.out.persistence.JpaCarroRepository;
import com.example.veiculosdb.domain.v1.model.Carro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarroRepositoryAdapterTest {

    @Mock
    private JpaCarroRepository jpaCarroRepository;

    private CarroRepositoryAdapter carroRepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carroRepositoryAdapter = new CarroRepositoryAdapter(jpaCarroRepository);
    }

    @Test
    void deveSalvarCarro() {
        Carro carro = new Carro();
        carro.setPlaca("ABC1234");

        when(jpaCarroRepository.save(carro)).thenReturn(carro);

        Carro salvo = carroRepositoryAdapter.save(carro);

        assertThat(salvo).isEqualTo(carro);
        verify(jpaCarroRepository, times(1)).save(carro);
    }

    @Test
    void deveBuscarTodosOsCarros() {
        List<Carro> lista = List.of(new Carro(), new Carro());
        when(jpaCarroRepository.findAll()).thenReturn(lista);

        List<Carro> resultado = carroRepositoryAdapter.findAll();

        assertThat(resultado).hasSize(2);
        verify(jpaCarroRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarCarroPorPlaca() {
        Carro carro = new Carro();
        carro.setPlaca("XYZ9876");

        when(jpaCarroRepository.findByPlaca("XYZ9876")).thenReturn(Optional.of(carro));

        Optional<Carro> resultado = carroRepositoryAdapter.findByPlaca("XYZ9876");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getPlaca()).isEqualTo("XYZ9876");
    }

    @Test
    void deveDeletarCarro() {
        Carro carro = new Carro();
        carro.setPlaca("DEL1234");

        doNothing().when(jpaCarroRepository).delete(carro);

        carroRepositoryAdapter.delete(carro);

        verify(jpaCarroRepository, times(1)).delete(carro);
    }

    @Test
    void deveBuscarPorMarcaETipo() {
        Pageable pageable = PageRequest.of(0, 10);
        Carro carro = new Carro();
        carro.setMarca("Toyota");
        carro.setTipo("SUV");

        Page<Carro> page = new PageImpl<>(List.of(carro));
        when(jpaCarroRepository.buscarPorMarcaTipo("Toyota", "SUV", pageable)).thenReturn(page);

        Page<Carro> resultado = carroRepositoryAdapter.buscarPorMarcaTipo("Toyota", "SUV", pageable);

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).getMarca()).isEqualTo("Toyota");
        assertThat(resultado.getContent().get(0).getTipo()).isEqualTo("SUV");
    }
}
