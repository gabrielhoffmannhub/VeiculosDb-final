package com.example.veiculosdb.domain.model;
import com.example.veiculosdb.exception.CarroNotFoundException;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

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

    public BigDecimal calcularDepreciacao() {

        int anoAtual = LocalDate.now().getYear();
        int anosDeUso = anoAtual - this.getAnoFabricacao();

        if (anosDeUso <= 0) {
            return this.getValorMercado();
        }

        double taxaDepreciacaoAnual = 0.05;
        double fatorDepreciacao = Math.pow(1 - taxaDepreciacaoAnual, anosDeUso);
        BigDecimal valorFinal = this.getValorMercado().multiply(BigDecimal.valueOf(fatorDepreciacao));
        return valorFinal.setScale(2, RoundingMode.HALF_UP);
    }
}
