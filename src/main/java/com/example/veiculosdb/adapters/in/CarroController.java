package com.example.veiculosdb.adapters.in;

import com.example.veiculosdb.dto.v1.CarroRequestDTO;
import com.example.veiculosdb.dto.v1.CarroResponseDTO;
import com.example.veiculosdb.domain.v1.model.Carro;
import com.example.veiculosdb.application.CarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/carros")
@RequiredArgsConstructor
public class CarroController {

    private final CarroService carroService;

    @PostMapping
    public ResponseEntity<CarroResponseDTO> criarCarro(@RequestBody CarroRequestDTO dto) {
        Carro carro = new Carro();
        carro.setMarca(dto.getMarca());
        carro.setModelo(dto.getModelo());
        carro.setAnoFabricacao(dto.getAnoFabricacao());
        carro.setPlaca(dto.getPlaca());
        carro.setTipo(dto.getTipo());
        carro.setValorMercado(dto.getValorMercado());

        Carro carroSalvo = carroService.salvar(carro);

        CarroResponseDTO responseDTO = new CarroResponseDTO(carroSalvo);

        URI location = URI.create("/carros/" + carroSalvo.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<CarroResponseDTO>> listarComPaginacaoEFiltro(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String tipo
    ) {
        Page<Carro> resultado = carroService.buscarComFiltro(marca, tipo, page, size);
        Page<CarroResponseDTO> response = resultado.map(CarroResponseDTO::new);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{placa}")
    public ResponseEntity<CarroResponseDTO> buscarPorPlaca(@PathVariable String placa) {
        return carroService.buscarPorPlaca(placa)
                .map(carro -> {
                    CarroResponseDTO response = new CarroResponseDTO();
                    response.setId(carro.getId());
                    response.setMarca(carro.getMarca());
                    response.setModelo(carro.getModelo());
                    response.setAnoFabricacao(carro.getAnoFabricacao());
                    response.setPlaca(carro.getPlaca());
                    response.setTipo(carro.getTipo());
                    response.setValorMercado(carro.getValorMercado());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{placa}/depreciacao")
    public ResponseEntity<BigDecimal> calcularDepreciacao(@PathVariable String placa) {
        return carroService.buscarPorPlaca(placa)
                .map(carro -> {
                    BigDecimal valorDepreciado = carroService.calcularDepreciacao(carro);
                    return ResponseEntity.ok(valorDepreciado);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{placa}")
    public ResponseEntity<Carro> atualizarCarro(@PathVariable String placa, @RequestBody CarroRequestDTO dto) {
        return carroService.atualizarPorPlaca(placa, dto)
                .map(carroAtualizado -> ResponseEntity.ok().body(carroAtualizado))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> deletarCarro(@PathVariable String placa) {
        if (carroService.deletarPorPlaca(placa)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
