package com.example.veiculosdb;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CarroControllerReadTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveListarCarrosComSucesso() throws Exception {
        String carroJson = """
            {
                "marca": "Honda",
                "modelo": "Civic",
                "anoFabricacao": 2021,
                "placa": "LST1234",
                "tipo": "Sedan",
                "valorMercado": 100000
            }
        """;

        String token = obterTokenJWT();

        mockMvc.perform(get("/api/v1/carros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].marca").value("Chevrolet"))
                .andExpect(jsonPath("$.content[1].marca").value("Honda"))
                .andExpect(jsonPath("$.content.length()").value(2));  // Verificar o número de elementos


        mockMvc.perform(get("/api/v1/carros")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
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
