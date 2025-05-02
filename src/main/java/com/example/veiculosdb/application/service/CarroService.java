package com.example.veiculosdb.application.service;

import com.example.veiculosdb.ports.input.CarroServicePort;
import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import com.example.veiculosdb.domain.model.Carro;
import com.example.veiculosdb.ports.output.CarroRepositoryPort;
import com.example.veiculosdb.exception.CarroNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarroService implements CarroServicePort {

    private final CarroRepositoryPort carroRepositoryPort;

    @Override
    public CarroResponseDTO salvar(CarroRequestDTO dto) {
        Carro carro = new Carro(null, dto.getMarca(), dto.getModelo(), dto.getAnoFabricacao(), dto.getPlaca(), dto.getTipo(), dto.getValorMercado());
        Carro salvo = carroRepositoryPort.salvar(carro);
        return new CarroResponseDTO(salvo);
    }

    @Override
    public List<CarroResponseDTO> listarTodos() {
        return carroRepositoryPort.listarTodos()
                .stream()
                .map(CarroResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CarroResponseDTO buscarPorPlaca(String placa) {
        Carro carro = carroRepositoryPort.buscarPorPlaca(placa)
                .orElseThrow(() -> new CarroNotFoundException("Carro com placa " + placa + " não encontrado"));
        return new CarroResponseDTO(carro);
    }

    @Override
    public CarroResponseDTO atualizarPorPlaca(String placa, CarroRequestDTO dto) {
        Carro carro = carroRepositoryPort.buscarPorPlaca(placa)
                .orElseThrow(() -> new CarroNotFoundException("Carro com placa " + placa + " não encontrado"));

        carro.setMarca(dto.getMarca());
        carro.setModelo(dto.getModelo());
        carro.setAnoFabricacao(dto.getAnoFabricacao());
        carro.setTipo(dto.getTipo());
        carro.setValorMercado(dto.getValorMercado());
        Carro atualizado = carroRepositoryPort.salvar(carro);
        return new CarroResponseDTO(atualizado);
    }

    @Override
    public void deletarPorPlaca(String placa) {
        Carro carro = carroRepositoryPort.buscarPorPlaca(placa)
                .orElseThrow(() -> new CarroNotFoundException("Carro com placa " + placa + " não encontrado"));

        carroRepositoryPort.deletar(carro);
    }

    @Override
    public BigDecimal calcularDepreciacao(String placa) {
        Carro carro = carroRepositoryPort.buscarPorPlaca(placa)
                .orElseThrow(() -> new CarroNotFoundException("Carro com placa " + placa + " não encontrado"));
       return carro.calcularDepreciacao();
    }

}
