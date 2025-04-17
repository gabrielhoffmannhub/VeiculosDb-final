package com.example.veiculosdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CarroControllerDepreciacaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCalcularDepreciacaoComSucesso() throws Exception {
        String carroJson = """
            {
                "marca": "Toyota",
                "modelo": "Corolla",
                "anoFabricacao": 2022,
                "placa": "XYZ1234",
                "tipo": "Sedan",
                "valorMercado": 90000
            }
        """;

        mockMvc.perform(post("/api/v1/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carroJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/carros/XYZ1234/depreciacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }
}
