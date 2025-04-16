package com.example.veiculosdb.domain.v1.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private Integer anoFabricacao;
    private String placa;
    private String tipo;
    private BigDecimal valorMercado;
}
