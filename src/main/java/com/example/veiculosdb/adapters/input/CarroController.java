package com.example.veiculosdb.adapters.input;

import com.example.veiculosdb.dto.CarroRequestDTO;
import com.example.veiculosdb.dto.CarroResponseDTO;
import com.example.veiculosdb.exception.CarroNotFoundException;
import com.example.veiculosdb.ports.input.CarroServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
@RequiredArgsConstructor
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
        try {
            CarroResponseDTO carroResponseDTO = carroServicePort.buscarPorPlaca(placa);
            return ResponseEntity.ok(carroResponseDTO);
        } catch (CarroNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{placa}")
    public ResponseEntity<CarroResponseDTO> atualizarCarro(@PathVariable String placa, @RequestBody CarroRequestDTO dto) {
        try {
            CarroResponseDTO atualizado = carroServicePort.atualizarPorPlaca(placa, dto);
            return ResponseEntity.ok(atualizado);
        } catch (CarroNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> deletarCarro(@PathVariable String placa) {
        try {
            carroServicePort.deletarPorPlaca(placa);
            return ResponseEntity.noContent().build();
        } catch (CarroNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{placa}/depreciacao")
    public ResponseEntity<BigDecimal> calcularDepreciacao(@PathVariable String placa) {
        try {
            BigDecimal valorDepreciado = carroServicePort.calcularDepreciacao(placa);
            return ResponseEntity.ok(valorDepreciado);
        } catch (CarroNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
