package com.example.veiculosdb.adapters.output;

import com.example.veiculosdb.adapters.output.persistence.CarroRepository;
import com.example.veiculosdb.domain.model.Carro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CarroRepositoryTest {

    private CarroRepository carroRepository;

    @BeforeEach
    public void setup() {
        carroRepository = new CarroRepository();
        carroRepository.salvar(new Carro(null, "Volkswagen", "Fusca", 1980, "ABC1234", "Hatch", new BigDecimal("15000.00")));
        carroRepository.salvar(new Carro(null, "Volkswagen", "Gol", 2010, "XYZ5678", "Hatch", new BigDecimal("25000.00")));
    }

    @Test
    public void deveSalvarCarro() {
        Carro carro = new Carro(null, "Honda", "Civic", 2020, "ZZZ9999", "Sedan", new BigDecimal("70000.00"));
        Carro carroSalvo = carroRepository.salvar(carro);

        assertThat(carroSalvo).isNotNull();
        assertThat(carroSalvo.getId()).isNotNull();
        assertThat(carroSalvo.getModelo()).isEqualTo("Civic");
    }

    @Test
    public void deveListarTodosOsCarros() {
        List<Carro> carros = carroRepository.listarTodos();

        assertThat(carros).hasSize(2);
        assertThat(carros.get(0).getModelo()).isEqualTo("Fusca");
        assertThat(carros.get(1).getModelo()).isEqualTo("Gol");
    }

    @Test
    public void deveBuscarCarroPorPlaca() {
        Optional<Carro> carro = carroRepository.buscarPorPlaca("ABC1234");

        assertThat(carro).isPresent();
        assertThat(carro.get().getModelo()).isEqualTo("Fusca");
    }

    @Test
    public void deveDeletarCarro() {
        Optional<Carro> carro = carroRepository.buscarPorPlaca("ABC1234");
        assertThat(carro).isPresent();

        carroRepository.deletar(carro.get());

        Optional<Carro> carroDeletado = carroRepository.buscarPorPlaca("ABC1234");
        assertThat(carroDeletado).isEmpty();
    }
}
