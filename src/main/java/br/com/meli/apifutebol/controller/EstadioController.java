package br.com.meli.apifutebol.controller;

import br.com.meli.apifutebol.dto.EstadioDto;
import br.com.meli.apifutebol.service.EstadioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estadio")
public class EstadioController {
    private final EstadioService estadioService;
    public EstadioController(EstadioService estadioService) {
        this.estadioService = estadioService;
    }

    @GetMapping
    public ResponseEntity<List<EstadioDto>> getEstadio(){
        List<EstadioDto> estadio = estadioService.getEstadio();
        return ResponseEntity.ok(estadio);
    }

    @PostMapping
    public ResponseEntity<EstadioDto> cadastrar(@RequestBody EstadioDto estadioDto) {
        EstadioDto criado = estadioService.cadastrarEstadio(estadioDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
