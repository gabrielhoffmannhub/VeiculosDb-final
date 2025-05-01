//package com.example.veiculosdb.adapters.output;
//
//import com.example.veiculosdb.domain.model.Carro;
//import com.example.veiculosdb.ports.output.CarroRepositoryPort;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CarroRepositoryTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private CarroRepositoryPort carroRepository;
//
//    @BeforeEach
//    public void setup() {
//        carroRepository.salvar(new Carro(null, "Volkswagen", "Fusca", 1980, "ABC1234", "Hatch", new BigDecimal("15000.00")));
//        carroRepository.salvar(new Carro(null, "Volkswagen", "Gol", 2010, "XYZ5678", "Hatch", new BigDecimal("25000.00")));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", password = "1234", roles = "USER")
//    public void deveSalvarCarro() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/carros")
//                        .param("placa", "ZZZ9999")
//                        .param("modelo", "Civic"))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        Optional<Carro> carro = carroRepository.buscarPorPlaca("ZZZ9999");
//        assertThat(carro).isPresent();
//        assertThat(carro.get().getModelo()).isEqualTo("Civic");
//    }
//
//    @Test
//    @WithMockUser(username = "admin", password = "1234", roles = "USER")
//    public void deveBuscarTodosOsCarros() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/carros"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].modelo").value("Fusca"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].modelo").value("Gol"));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", password = "1234", roles = "USER")
//    public void deveDeletarCarro() throws Exception {
//        Carro carro = new Carro(null, "Honda", "Civic", 2020, "AAA0000", "Sedan", new BigDecimal("70000.00"));
//        carro = carroRepository.salvar(carro);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/carros/{id}", carro.getId()))
//                .andExpect(MockMvcResultMatchers.status().isNoContent());
//
//        Optional<Carro> carroDeletado = carroRepository.buscarPorPlaca("AAA0000");
//        assertThat(carroDeletado).isEmpty();
//    }
//}
