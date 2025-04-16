package com.example.veiculosdb.dto.v1;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarroRequestDTO {

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @Min(value = 1975)
    private int anoFabricacao;

    @NotBlank
    private String placa;

    @NotBlank
    private String tipo;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal valorMercado;
}
