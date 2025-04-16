package com.example.veiculosdb.application;

import com.example.veiculosdb.domain.v1.ports.in.CarroUseCase;
import com.example.veiculosdb.dto.v1.CarroRequestDTO;
import com.example.veiculosdb.domain.v1.model.Carro;
import com.example.veiculosdb.domain.v1.ports.out.CarroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarroService implements CarroUseCase {

    private final CarroRepository carroRepository;

    public Carro salvar(Carro carro) {
        return carroRepository.save(carro);
    }

    public List<Carro> listarTodos() {
        return carroRepository.findAll();
    }

    public Optional<Carro> buscarPorPlaca(String placa) {
        return carroRepository.findByPlaca(placa);
    }

    public Optional<Carro> atualizarPorPlaca(String placa, CarroRequestDTO dto) {
        return carroRepository.findByPlaca(placa).map(carro -> {
            carro.setMarca(dto.getMarca());
            carro.setModelo(dto.getModelo());
            carro.setAnoFabricacao(dto.getAnoFabricacao());
            carro.setTipo(dto.getTipo());
            carro.setValorMercado(dto.getValorMercado());
            return carroRepository.save(carro);
        });
    }

    public boolean deletarPorPlaca(String placa) {
        return carroRepository.findByPlaca(placa).map(carro -> {
            carroRepository.delete(carro);
            return true;
        }).orElse(false);
    }
    public BigDecimal calcularDepreciacao(Carro carro) {
        int anoAtual = LocalDate.now().getYear();
        int anosDeUso = anoAtual - carro.getAnoFabricacao();

        if (anosDeUso <= 0) {
            return carro.getValorMercado();
        }

        double taxaDepreciacaoAnual = 0.05; // 5%
        double fatorDepreciacao = Math.pow(1 - taxaDepreciacaoAnual, anosDeUso);
        BigDecimal valorFinal = carro.getValorMercado().multiply(BigDecimal.valueOf(fatorDepreciacao));

        return valorFinal.setScale(2, RoundingMode.HALF_UP);
    }

    public Page<Carro> buscarComFiltro(String marca, String tipo, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return carroRepository.buscarPorMarcaTipo(marca, tipo, pageable);
    }
}
