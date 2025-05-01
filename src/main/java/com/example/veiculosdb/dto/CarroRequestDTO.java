package com.example.veiculosdb.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CarroRequestDTO {

    @NotBlank
    private final String marca;

    @NotBlank
    private final String modelo;

    @Min(value = 1975)
    private final int anoFabricacao;

    @NotBlank
    private final String placa;

    @NotBlank
    private final String tipo;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private final BigDecimal valorMercado;
}
