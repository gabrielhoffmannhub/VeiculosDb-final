package com.example.veiculosdb.adapters.out.persistence;

import com.example.veiculosdb.domain.v1.model.Carro;
import com.example.veiculosdb.domain.v1.ports.out.CarroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarroRepositoryAdapter implements CarroRepository {

    private final JpaCarroRepository jpaRepository;

    @Override
    public Carro save(Carro carro) {
        return jpaRepository.save(carro);
    }

    @Override
    public List<Carro> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Carro> findByPlaca(String placa) {
        return jpaRepository.findByPlaca(placa);
    }

    @Override
    public void delete(Carro carro) {
        jpaRepository.delete(carro);
    }

    @Override
    public Page<Carro> buscarPorMarcaTipo(String marca, String tipo, Pageable pageable) {
        return jpaRepository.buscarPorMarcaTipo(marca, tipo, pageable);
    }
}
