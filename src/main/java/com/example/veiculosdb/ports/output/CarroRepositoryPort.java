package com.example.veiculosdb.ports.output;

import com.example.veiculosdb.domain.model.Carro;
import java.util.List;
import java.util.Optional;

public interface CarroRepositoryPort {
    Carro salvar(Carro carro);
    List<Carro> listarTodos();
    Optional<Carro> buscarPorPlaca(String placa);
    void deletar(Carro carro);
}


