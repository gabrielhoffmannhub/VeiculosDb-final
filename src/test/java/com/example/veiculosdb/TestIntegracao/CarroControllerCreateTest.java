package com.example.veiculosdb.TestIntegracao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CarroControllerCreateTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCriarCarroComSucesso() throws Exception {
        String carroJson = """
            {
                "marca": "Chevrolet",
                "modelo": "Onix",
                "anoFabricacao": 2020,
                "placa": "CRZ1234",
                "tipo": "Hatch",
                "valorMercado": 60000
            }
        """;

        String token = obterTokenJWT();

        mockMvc.perform(post("/api/v1/carros")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.placa").value("CRZ1234"));
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
                .andExpect(status().isOk()) // Espera um status 200 (OK)
                .andReturn();

        return result.getResponse().getContentAsString();
    }
}
