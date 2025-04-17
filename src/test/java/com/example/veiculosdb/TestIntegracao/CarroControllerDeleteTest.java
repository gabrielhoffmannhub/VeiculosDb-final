package com.example.veiculosdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CarroControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveDeletarCarroComSucesso() throws Exception {
        String carroJson = """
            {
                "marca": "Fiat",
                "modelo": "Argo",
                "anoFabricacao": 2019,
                "placa": "DEL1234",
                "tipo": "Hatch",
                "valorMercado": 50000
            }
        """;

        String token = obterTokenJWT();

        mockMvc.perform(post("/api/v1/carros")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/v1/carros/DEL1234")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
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
