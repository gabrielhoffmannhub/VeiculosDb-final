package com.example.veiculosdb;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CarroControllerUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveAtualizarCarroComSucesso() throws Exception {
        String carroJson = """
            {
                "marca": "Hyundai",
                "modelo": "HB20",
                "anoFabricacao": 2020,
                "placa": "XYZ5678",
                "tipo": "Hatch",
                "valorMercado": 52000
            }
        """;

        String token = obterTokenJWT();

        mockMvc.perform(put("/api/v1/carros/XYZ5678")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isOk());
    }

    private String obterTokenJWT() throws Exception {
        String loginJson = """
            {
                "username": "admin",
                "password": "admin"
            }
        """;

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }
}
