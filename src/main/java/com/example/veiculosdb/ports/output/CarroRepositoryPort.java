package com.example.veiculosdb.ports.output;

import com.example.veiculosdb.domain.model.Carro;
import java.util.List;
import java.util.Optional;

public interface CarroRepositoryPort {
    Carro save(Carro carro);
    List<Carro> findAll();
    Optional<Carro> findByPlaca(String placa);
    void delete(Carro carro);
}


