package com.example.veiculosdb.domain.v1.ports.out;

import com.example.veiculosdb.domain.v1.model.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CarroRepository {
    Carro save(Carro carro);
    List<Carro> findAll();
    Optional<Carro> findByPlaca(String placa);
    void delete(Carro carro);
    Page<Carro> buscarPorMarcaTipo(String marca, String tipo, Pageable pageable);
}


