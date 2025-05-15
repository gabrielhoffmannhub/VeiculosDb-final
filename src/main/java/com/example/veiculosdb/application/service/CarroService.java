package com.example.veiculosdb.application.service;

import com.example.veiculosdb.ports.input.CarroServicePort;
import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import com.example.veiculosdb.domain.model.Carro;
import com.example.veiculosdb.ports.output.CarroRepositoryPort;
import com.example.veiculosdb.exception.InvalidCarroException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarroService implements CarroServicePort {

    private final CarroRepositoryPort carroRepositoryPort;

    @Override
    public CarroResponseDTO salvar(CarroRequestDTO dto) {
        Carro carro = new Carro(null, dto.getMarca(), dto.getModelo(), dto.getAnoFabricacao(), dto.getPlaca(), dto.getTipo(), dto.getValorMercado());
        carro.validar();
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
    public Optional<CarroResponseDTO> buscarPorPlaca(String placa) {
        return carroRepositoryPort.buscarPorPlaca(placa)
                .map(CarroResponseDTO::new);
    }

    @Override
    public Optional<CarroResponseDTO> atualizarPorPlaca(String placa, CarroRequestDTO dto) {
        return carroRepositoryPort.buscarPorPlaca(placa)
                .map(carro -> {
                    carro.setMarca(dto.getMarca());
                    carro.setModelo(dto.getModelo());
                    carro.setAnoFabricacao(dto.getAnoFabricacao());
                    carro.setTipo(dto.getTipo());
                    carro.setValorMercado(dto.getValorMercado());
                    carro.validar();
                    Carro atualizado = carroRepositoryPort.salvar(carro);
                    return new CarroResponseDTO(atualizado);
                });
    }

    @Override
    public void deletarPorPlaca(String placa) {
        Carro carro = carroRepositoryPort.buscarPorPlaca(placa)
                .orElseThrow(() -> new InvalidCarroException("Carro com placa " + placa + " não encontrado"));
        carroRepositoryPort.deletar(carro);
    }

    @Override
    public BigDecimal calcularDepreciacao(String placa) {
        Carro carro = carroRepositoryPort.buscarPorPlaca(placa)
                .orElseThrow(() -> new InvalidCarroException("Carro com placa " + placa + " não encontrado"));
        return carro.calcularDepreciacao();
    }
}