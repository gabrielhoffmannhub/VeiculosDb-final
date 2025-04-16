package com.example.veiculosdb.adapters.out.persistence;

import com.example.veiculosdb.domain.v1.model.Carro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaCarroRepository extends JpaRepository<Carro, Long> {

    Optional<Carro> findByPlaca(String placa);

    @Query("SELECT c FROM Carro c WHERE (:marca IS NULL OR c.marca = :marca) AND (:tipo IS NULL OR c.tipo = :tipo)")
    Page<Carro> buscarPorMarcaTipo(@Param("marca") String marca, @Param("tipo") String tipo, Pageable pageable);
}
