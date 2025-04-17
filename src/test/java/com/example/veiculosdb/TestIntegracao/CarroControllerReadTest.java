package com.example.veiculosdb.TestIntegracao;

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
        String token = obterTokenJWT();

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

        mockMvc.perform(post("/api/v1/carros")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/carros")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
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
