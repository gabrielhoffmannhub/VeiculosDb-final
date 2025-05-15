package com.example.veiculosdb.adapters.input;

import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import com.example.veiculosdb.exception.InvalidCarroException;
import com.example.veiculosdb.ports.input.CarroServicePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class CarroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class MockConfig {
        @Bean
        @Primary
        public CarroServicePort carroServicePort() {
            return Mockito.mock(CarroServicePort.class);
        }
    }

    @Autowired
    private CarroServicePort carroServicePort;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", password = "{noop}1234", roles = "USER")
    void testCriarCarro() throws Exception {
        CarroRequestDTO request = new CarroRequestDTO("Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        CarroResponseDTO response = new CarroResponseDTO(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
        when(carroServicePort.salvar(argThat(dto ->
                dto.getMarca().equals("Fiat") &&
                        dto.getModelo().equals("Uno") &&
                        dto.getAnoFabricacao() == 2020 &&
                        dto.getPlaca().equals("ABC1234") &&
                        dto.getTipo().equals("Hatch") &&
                        dto.getValorMercado().compareTo(BigDecimal.valueOf(30000)) == 0
        ))).thenReturn(response);

        mockMvc.perform(post("/api/v1/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/carros/1"))
                .andExpect(jsonPath("$.id").value(1));

        verify(carroServicePort).salvar(argThat(dto ->
                dto.getMarca().equals("Fiat") &&
                        dto.getModelo().equals("Uno") &&
                        dto.getAnoFabricacao() == 2020 &&
                        dto.getPlaca().equals("ABC1234") &&
                        dto.getTipo().equals("Hatch") &&
                        dto.getValorMercado().compareTo(BigDecimal.valueOf(30000)) == 0
        ));
    }

    @Test
    @WithMockUser(username = "admin", password = "{noop}1234", roles = "USER")
    void testAtualizarCarro_ComSucesso() throws Exception {
        CarroRequestDTO request = new CarroRequestDTO("Fiat", "Palio", 2019, "ABC1234", "Hatch", BigDecimal.valueOf(25000));
        CarroResponseDTO response = new CarroResponseDTO(1L, "Fiat", "Palio", 2019, "ABC1234", "Hatch", BigDecimal.valueOf(25000));

        when(carroServicePort.atualizarPorPlaca(eq("ABC1234"), argThat(dto ->
                dto.getMarca().equals("Fiat") &&
                        dto.getModelo().equals("Palio") &&
                        dto.getAnoFabricacao() == 2019 &&
                        dto.getPlaca().equals("ABC1234") &&
                        dto.getTipo().equals("Hatch") &&
                        dto.getValorMercado().compareTo(BigDecimal.valueOf(25000)) == 0
        ))).thenReturn(Optional.of(response));

        mockMvc.perform(put("/api/v1/carros/ABC1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Palio"));

        verify(carroServicePort).atualizarPorPlaca(eq("ABC1234"), argThat(dto ->
                dto.getMarca().equals("Fiat") &&
                        dto.getModelo().equals("Palio") &&
                        dto.getAnoFabricacao() == 2019 &&
                        dto.getPlaca().equals("ABC1234") &&
                        dto.getTipo().equals("Hatch") &&
                        dto.getValorMercado().compareTo(BigDecimal.valueOf(25000)) == 0
        ));
    }

    @Test
    @WithMockUser(username = "admin", password = "{noop}1234", roles = "USER")
    void testAtualizarCarro_NaoEncontrado() throws Exception {
        CarroRequestDTO request = new CarroRequestDTO("Fiat", "Palio", 2019, "XYZ9999", "Hatch", BigDecimal.valueOf(25000));

        when(carroServicePort.atualizarPorPlaca(eq("XYZ9999"), argThat(dto ->
                dto.getMarca().equals("Fiat") &&
                        dto.getModelo().equals("Palio") &&
                        dto.getAnoFabricacao() == 2019 &&
                        dto.getPlaca().equals("XYZ9999") &&
                        dto.getTipo().equals("Hatch") &&
                        dto.getValorMercado().compareTo(BigDecimal.valueOf(25000)) == 0
        ))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/carros/XYZ9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(carroServicePort).atualizarPorPlaca(eq("XYZ9999"), argThat(dto ->
                dto.getMarca().equals("Fiat") &&
                        dto.getModelo().equals("Palio") &&
                        dto.getAnoFabricacao() == 2019 &&
                        dto.getPlaca().equals("XYZ9999") &&
                        dto.getTipo().equals("Hatch") &&
                        dto.getValorMercado().compareTo(BigDecimal.valueOf(25000)) == 0
        ));
    }

    @Test
    @WithMockUser(username = "admin", password = "{noop}1234", roles = "USER")
    void testDeletarCarro() throws Exception {
        doNothing().when(carroServicePort).deletarPorPlaca("ABC1234");

        mockMvc.perform(delete("/api/v1/carros/ABC1234"))
                .andExpect(status().isNoContent());

        verify(carroServicePort).deletarPorPlaca("ABC1234");
    }

    @Test
    @WithMockUser(username = "admin", password = "{noop}1234", roles = "USER")
    void testDeletarCarro_NaoEncontrado() throws Exception {
        doThrow(new InvalidCarroException("Carro não encontrado")).when(carroServicePort).deletarPorPlaca("XYZ9999");

        mockMvc.perform(delete("/api/v1/carros/XYZ9999"))
                .andExpect(status().isNotFound());

        verify(carroServicePort).deletarPorPlaca("XYZ9999");
    }

    @Test
    @WithMockUser(username = "admin", password = "{noop}1234", roles = "USER")
    void testCalcularDepreciacao_ComSucesso() throws Exception {
        BigDecimal valorDepreciado = BigDecimal.valueOf(20000);
        when(carroServicePort.calcularDepreciacao("ABC1234")).thenReturn(valorDepreciado);

        mockMvc.perform(get("/api/v1/carros/ABC1234/depreciacao"))
                .andExpect(status().isOk())
                .andExpect(content().string("20000"));

        verify(carroServicePort).calcularDepreciacao("ABC1234");
    }

    @Test
    @WithMockUser(username = "admin", password = "{noop}1234", roles = "USER")
    void testCalcularDepreciacao_NaoEncontrado() throws Exception {
        when(carroServicePort.calcularDepreciacao("XYZ9999")).thenThrow(new InvalidCarroException("Não encontrado"));

        mockMvc.perform(get("/api/v1/carros/XYZ9999/depreciacao"))
                .andExpect(status().isNotFound());

        verify(carroServicePort).calcularDepreciacao("XYZ9999");
    }
}