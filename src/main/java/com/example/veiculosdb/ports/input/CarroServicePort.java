package com.example.veiculosdb.ports.input;

import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import java.math.BigDecimal;
import java.util.List;

public interface CarroServicePort {

    CarroResponseDTO salvar(CarroRequestDTO dto);

    List<CarroResponseDTO> listarTodos();

    CarroResponseDTO buscarPorPlaca(String placa);

    CarroResponseDTO atualizarPorPlaca(String placa, CarroRequestDTO dto);

    void deletarPorPlaca(String placa);

    BigDecimal calcularDepreciacao(String placa);
}
