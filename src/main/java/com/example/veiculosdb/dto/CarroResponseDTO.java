package com.example.veiculosdb.dto;

import com.example.veiculosdb.domain.model.Carro;
import lombok.Getter;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CarroResponseDTO {
    private final Long id;
    private final String marca;
    private final String modelo;
    private final int anoFabricacao;
    private final String placa;
    private final String tipo;
    private final BigDecimal valorMercado;

    public CarroResponseDTO(Carro carro) {
        this.id = carro.getId();
        this.marca = carro.getMarca();
        this.modelo = carro.getModelo();
        this.anoFabricacao = carro.getAnoFabricacao();
        this.placa = carro.getPlaca();
        this.tipo = carro.getTipo();
        this.valorMercado = carro.getValorMercado();
    }
}
