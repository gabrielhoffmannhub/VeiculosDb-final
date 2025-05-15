package com.example.veiculosdb.domain.model;

import com.example.veiculosdb.exception.InvalidCarroException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarroTest {

    @Test
    void deveCriarCarroComSucesso() {
        Carro carro = new Carro("Fiat", "Uno", 2020, "ABC1234", "Hatch", new BigDecimal("30000.00"));

        assertThat(carro).isNotNull();
        assertThat(carro.getMarca()).isEqualTo("Fiat");
        assertThat(carro.getModelo()).isEqualTo("Uno");
        assertThat(carro.getAnoFabricacao()).isEqualTo(2020);
        assertThat(carro.getPlaca()).isEqualTo("ABC1234");
        assertThat(carro.getTipo()).isEqualTo("Hatch");
        assertThat(carro.getValorMercado()).isEqualTo(new BigDecimal("30000.00"));
    }

    @Test
    void deveLancarExcecaoQuandoMarcaForInvalida() {
        assertThatThrownBy(() -> new Carro(null, "Uno", 2020, "ABC1234", "Hatch", new BigDecimal("30000.00")))
                .isInstanceOf(InvalidCarroException.class)
                .hasMessage("Marca não pode ser vazia.");
    }

    @Test
    void deveLancarExcecaoQuandoModeloForInvalido() {
        assertThatThrownBy(() -> new Carro("Fiat", null, 2020, "ABC1234", "Hatch", new BigDecimal("30000.00")))
                .isInstanceOf(InvalidCarroException.class)
                .hasMessage("Modelo não pode ser vazio.");
    }

    @Test
    void deveLancarExcecaoQuandoAnoFabricacaoForInvalido() {
        assertThatThrownBy(() -> new Carro("Fiat", "Uno", 1800, "ABC1234", "Hatch", new BigDecimal("30000.00")))
                .isInstanceOf(InvalidCarroException.class)
                .hasMessage("Ano de fabricação inválido.");
    }

    @Test
    void deveLancarExcecaoQuandoPlacaForInvalida() {
        assertThatThrownBy(() -> new Carro("Fiat", "Uno", 2020, null, "Hatch", new BigDecimal("30000.00")))
                .isInstanceOf(InvalidCarroException.class)
                .hasMessage("Placa não pode ser vazia.");
    }

    @Test
    void deveLancarExcecaoQuandoTipoForInvalido() {
        assertThatThrownBy(() -> new Carro("Fiat", "Uno", 2020, "ABC1234", null, new BigDecimal("30000.00")))
                .isInstanceOf(InvalidCarroException.class)
                .hasMessage("Tipo não pode ser vazio.");
    }

    @Test
    void deveLancarExcecaoQuandoValorMercadoForInvalido() {
        assertThatThrownBy(() -> new Carro("Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.ZERO))
                .isInstanceOf(InvalidCarroException.class)
                .hasMessage("Valor de mercado deve ser maior que zero.");
    }

    @Test
    void deveAtualizarCarroComSucesso() {
        Carro carro = new Carro("Fiat", "Uno", 2020, "ABC1234", "Hatch", new BigDecimal("30000.00"));

        carro.atualizar("Fiat", "Palio", 2021, "Hatch", new BigDecimal("35000.00"));

        assertThat(carro.getModelo()).isEqualTo("Palio");
        assertThat(carro.getAnoFabricacao()).isEqualTo(2021);
        assertThat(carro.getValorMercado()).isEqualTo(new BigDecimal("35000.00"));
    }

    @Test
    void deveCalcularDepreciacaoCorretamente() {
        Carro carro = new Carro("Volkswagen", "Fusca", 1980, "ABC1234", "Hatch", new BigDecimal("15000.00"));

        BigDecimal depreciacao = carro.calcularDepreciacao();

        assertThat(depreciacao).isNotNull();
        assertThat(depreciacao).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    void deveLancarExcecaoQuandoAnoFabricacaoInvalidoParaDepreciacao() {
        Carro carro = new Carro("Volkswagen", "Fusca", 2025, "ABC1234", "Hatch", new BigDecimal("15000.00")) {
            @Override
            protected int obterAnoAtual() {
                return 2023;
            }
        };

        assertThatThrownBy(carro::calcularDepreciacao)
                .isInstanceOf(InvalidCarroException.class)
                .hasMessage("Ano de fabricação inválido para cálculo de depreciação.");
    }
}