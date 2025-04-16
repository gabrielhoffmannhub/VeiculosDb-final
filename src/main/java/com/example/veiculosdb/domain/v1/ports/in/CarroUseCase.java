package com.example.veiculosdb.domain.v1.ports.in;

import com.example.veiculosdb.domain.v1.model.Carro;
import com.example.veiculosdb.dto.v1.CarroRequestDTO;

import java.util.List;
import java.util.Optional;

public interface CarroUseCase {
    Carro salvar(Carro carro);
    List<Carro> listarTodos();
    Optional<Carro> buscarPorPlaca(String placa);
    Optional<Carro> atualizarPorPlaca(String placa, CarroRequestDTO dto);
    boolean deletarPorPlaca(String placa);
}
