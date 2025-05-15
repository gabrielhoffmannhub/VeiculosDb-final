package com.example.veiculosdb.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CarroRequestDTO {

    private final String marca;
    private final String modelo;
    private final int anoFabricacao;
    private final String placa;
    private final String tipo;
    private final BigDecimal valorMercado;
}