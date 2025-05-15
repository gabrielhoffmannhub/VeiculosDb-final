package com.example.veiculosdb.adapters.output.persistence;

import com.example.veiculosdb.domain.model.Carro;
import com.example.veiculosdb.ports.output.CarroRepositoryPort;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CarroRepository implements CarroRepositoryPort {

    private final List<Carro> carros = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Carro salvar(Carro carro) {
        carro.setId(idCounter.getAndIncrement());
        carros.add(carro);
        return carro;
    }

    @Override
    public List<Carro> listarTodos() {
        return carros;
    }

    @Override
    public Optional<Carro> buscarPorPlaca(String placa) {
        return carros.stream()
                .filter(carro -> carro.getPlaca().equals(placa))
                .findFirst();
    }

    @Override
    public void deletar(Carro carro) {
        carros.remove(carro);
    }
}
