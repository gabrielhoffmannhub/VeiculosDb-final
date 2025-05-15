package com.example.veiculosdb.ports.input;

import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CarroServicePort {

    CarroResponseDTO salvar(CarroRequestDTO dto);

    List<CarroResponseDTO> listarTodos();

    Optional<CarroResponseDTO> buscarPorPlaca(String placa);

    Optional<CarroResponseDTO> atualizarPorPlaca(String placa, CarroRequestDTO dto);

    void deletarPorPlaca(String placa);

    BigDecimal calcularDepreciacao(String placa);
}