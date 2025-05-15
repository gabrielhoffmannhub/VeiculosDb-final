package com.example.veiculosdb.adapters.input;

import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import com.example.veiculosdb.exception.InvalidCarroException;
import com.example.veiculosdb.ports.input.CarroServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
@RequiredArgsConstructor
@Slf4j
public class CarroController {

    private final CarroServicePort carroServicePort;

    @PostMapping
    public ResponseEntity<CarroResponseDTO> criarCarro(@RequestBody CarroRequestDTO dto) {
        CarroResponseDTO responseDTO = carroServicePort.salvar(dto);
        URI location = URI.create("/api/v1/carros/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CarroResponseDTO>> listarTodos() {
        List<CarroResponseDTO> carros = carroServicePort.listarTodos();
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/{placa}")
    public ResponseEntity<CarroResponseDTO> buscarPorPlaca(@PathVariable String placa) {
        return carroServicePort.buscarPorPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{placa}")
    public ResponseEntity<CarroResponseDTO> atualizarCarro(@PathVariable String placa, @RequestBody CarroRequestDTO dto) {
        return carroServicePort.atualizarPorPlaca(placa, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> deletarCarro(@PathVariable String placa) {
        try {
            carroServicePort.deletarPorPlaca(placa);
            return ResponseEntity.noContent().build();
        } catch (InvalidCarroException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{placa}/depreciacao")
    public ResponseEntity<BigDecimal> calcularDepreciacao(@PathVariable String placa) {
        try {
            BigDecimal valorDepreciado = carroServicePort.calcularDepreciacao(placa);
            return ResponseEntity.ok(valorDepreciado);
        } catch (InvalidCarroException e) {
            return ResponseEntity.notFound().build();
        }
    }
}