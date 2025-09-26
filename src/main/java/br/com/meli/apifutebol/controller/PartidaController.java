package br.com.meli.apifutebol.controller;

import br.com.meli.apifutebol.dto.PartidaDto;
import br.com.meli.apifutebol.dto.PartidaInputDto;
import br.com.meli.apifutebol.repository.PartidaRepository;
import br.com.meli.apifutebol.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/partida")
public class PartidaController {
    private final PartidaService partidaService;
    public PartidaController(PartidaService partidaService){this.partidaService = partidaService;}

    @GetMapping
    public ResponseEntity<Page<PartidaDto>> listarPartidas(Pageable pageable) {
        Page<PartidaDto> page = partidaService.listarPartidas(pageable);
        return ResponseEntity.ok(page);
    }
    @PostMapping
    public ResponseEntity<PartidaDto> criar(
            @RequestBody @Valid PartidaInputDto input) {

        PartidaDto criado = partidaService.cadastrarPartida(input);

        // opcional: devolve Location: /api/partida/{id}
        URI uri = URI.create("/api/partida/" + criado.getId());
        return ResponseEntity.created(uri).body(criado);
    }
}
