package com.example.veiculosdb.domain.model;

import com.example.veiculosdb.exception.InvalidCarroException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    public Carro(String marca, String modelo, Integer anoFabricacao, String placa, String tipo, BigDecimal valorMercado) {
        validar(marca, modelo, anoFabricacao, placa, tipo, valorMercado);
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.placa = placa;
        this.tipo = tipo;
        this.valorMercado = valorMercado;
    }

    public void atualizar(String marca, String modelo, Integer anoFabricacao, String tipo, BigDecimal valorMercado) {
        validar(marca, modelo, anoFabricacao, this.placa, tipo, valorMercado);
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.tipo = tipo;
        this.valorMercado = valorMercado;
    }

    public void validar() {
        validar(this.marca, this.modelo, this.anoFabricacao, this.placa, this.tipo, this.valorMercado);
    }

    private void validar(String marca, String modelo, Integer anoFabricacao, String placa, String tipo, BigDecimal valorMercado) {
        if (marca == null || marca.isBlank()) {
            throw new InvalidCarroException("Marca não pode ser vazia.");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new InvalidCarroException("Modelo não pode ser vazio.");
        }
        if (anoFabricacao == null || anoFabricacao < 1886) {
            throw new InvalidCarroException("Ano de fabricação inválido.");
        }
        if (placa == null || placa.isBlank()) {
            throw new InvalidCarroException("Placa não pode ser vazia.");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new InvalidCarroException("Tipo não pode ser vazio.");
        }
        if (valorMercado == null || valorMercado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidCarroException("Valor de mercado deve ser maior que zero.");
        }
    }

    public BigDecimal calcularDepreciacao() {
        int anoAtual = obterAnoAtual();
        if (this.anoFabricacao > anoAtual) {
            throw new InvalidCarroException("Ano de fabricação inválido para cálculo de depreciação.");
        }

        int anosDeUso = anoAtual - this.anoFabricacao;
        double taxaDepreciacaoAnual = 0.05;
        double fatorDepreciacao = Math.pow(1 - taxaDepreciacaoAnual, anosDeUso);
        BigDecimal valorFinal = this.valorMercado.multiply(BigDecimal.valueOf(fatorDepreciacao));
        return valorFinal.setScale(2, RoundingMode.HALF_UP);
    }

    protected int obterAnoAtual() {
        return Year.now().getValue();
    }
}