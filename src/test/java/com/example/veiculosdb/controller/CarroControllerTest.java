package com.example.veiculosdb.controller;

import com.example.veiculosdb.dto.v1.CarroRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarNovoCarro() throws Exception {
        CarroRequestDTO dto = new CarroRequestDTO("Toyota", "Corolla", 2020, "XYZ-9876", "Sedan", new BigDecimal("85000"));

        mockMvc.perform(post("/api/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveRetornarNotFoundParaPlacaInexistente() throws Exception {
        mockMvc.perform(get("/api/carros/placa/PLACAINEXISTENTE"))
                .andExpect(status().isNotFound());
    }
}
