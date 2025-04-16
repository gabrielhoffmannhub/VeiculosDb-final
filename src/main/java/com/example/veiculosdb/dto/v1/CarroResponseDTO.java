package com.example.veiculosdb.dto.v1;

import com.example.veiculosdb.domain.v1.model.Carro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarroResponseDTO {
    private Long id;
    private String marca;
    private String modelo;
    private int anoFabricacao;
    private String placa;
    private String tipo;
    private BigDecimal valorMercado;

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
