//package com.example.veiculosdb.adapters.input;
//
//import com.example.veiculosdb.config.SecurityConfig;
//import com.example.veiculosdb.dto.CarroRequestDTO;
//import com.example.veiculosdb.dto.CarroResponseDTO;
//import com.example.veiculosdb.exception.CarroNotFoundException;
//import com.example.veiculosdb.ports.input.CarroServicePort;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.TestConfiguration;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import java.math.BigDecimal;
//import java.util.List;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(CarroController.class)
//@Import(SecurityConfig.class)
//class CarroControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CarroServicePort carroServicePort;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @TestConfiguration
//    static class MockConfig {
//        @Bean
//        @Primary
//        CarroServicePort carroServicePort() {
//            return Mockito.mock(CarroServicePort.class);
//        }
//    }
//
//    @Test
//    void testCriarCarro() throws Exception {
//        CarroRequestDTO request = new CarroRequestDTO("Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
//        CarroResponseDTO response = new CarroResponseDTO(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
//
//        when(carroServicePort.salvar(any())).thenReturn(response);
//
//        mockMvc.perform(post("/api/v1/carros")
//                        .with(httpBasic("admin", "1234"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "/api/v1/carros/1"))
//                .andExpect(jsonPath("$.id").value(1));
//    }
//
//    @Test
//    void testListarTodos() throws Exception {
//        CarroResponseDTO response = new CarroResponseDTO(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
//        when(carroServicePort.listarTodos()).thenReturn(List.of(response));
//
//        mockMvc.perform(get("/api/v1/carros")
//                        .with(httpBasic("admin", "1234")))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1));
//    }
//
//    @Test
//    void testBuscarPorPlaca_ComSucesso() throws Exception {
//        CarroResponseDTO response = new CarroResponseDTO(1L, "Fiat", "Uno", 2020, "ABC1234", "Hatch", BigDecimal.valueOf(30000));
//        when(carroServicePort.buscarPorPlaca("ABC1234")).thenReturn(response);
//
//        mockMvc.perform(get("/api/v1/carros/ABC1234")
//                        .with(httpBasic("admin", "1234")))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.placa").value("ABC1234"));
//    }
//
//    @Test
//    void testBuscarPorPlaca_NaoEncontrado() throws Exception {
//        when(carroServicePort.buscarPorPlaca("XYZ9999")).thenThrow(new CarroNotFoundException("Não encontrado"));
//
//        mockMvc.perform(get("/api/v1/carros/XYZ9999")
//                        .with(httpBasic("admin", "1234")))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testAtualizarCarro() throws Exception {
//        CarroRequestDTO request = new CarroRequestDTO("Fiat", "Palio", 2019, "ABC1234", "Hatch", BigDecimal.valueOf(25000));
//        CarroResponseDTO response = new CarroResponseDTO(1L, "Fiat", "Palio", 2019, "ABC1234", "Hatch", BigDecimal.valueOf(25000));
//
//        when(carroServicePort.atualizarPorPlaca(eq("ABC1234"), any())).thenReturn(response);
//
//        mockMvc.perform(put("/api/v1/carros/ABC1234")
//                        .with(httpBasic("admin", "1234"))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.modelo").value("Palio"));
//    }
//
//    @Test
//    void testDeletarCarro() throws Exception {
//        mockMvc.perform(delete("/api/v1/carros/ABC1234")
//                        .with(httpBasic("admin", "1234")))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void testCalcularDepreciacao() throws Exception {
//        BigDecimal valorDepreciado = BigDecimal.valueOf(20000);
//        when(carroServicePort.calcularDepreciacao("ABC1234")).thenReturn(valorDepreciado);
//
//        mockMvc.perform(get("/api/v1/carros/ABC1234/depreciacao")
//                        .with(httpBasic("admin", "1234")))
//                .andExpect(status().isOk())
//                .andExpect(content().string("20000"));
//    }
//}
