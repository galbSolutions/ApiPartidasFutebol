package br.com.meli.apifutebol.controller;

import br.com.meli.apifutebol.dto.PageResponse;
import br.com.meli.apifutebol.dto.PartidaDto;
import br.com.meli.apifutebol.dto.PartidaInputDto;
import br.com.meli.apifutebol.repository.PartidaRepository;
import br.com.meli.apifutebol.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/partida")
public class PartidaController {
    private final PartidaService partidaService;
    public PartidaController(PartidaService partidaService){this.partidaService = partidaService;}

   /* @GetMapping
    public ResponseEntity<Page<PartidaDto>> listarPartidas(Pageable pageable) {
        Page<PartidaDto> page = partidaService.listarPartidas(pageable);
        return ResponseEntity.ok(page);
    }*/
    @PostMapping
    public ResponseEntity<PartidaDto> criar(
            @RequestBody @Valid PartidaInputDto input) {
        PartidaDto criado = partidaService.cadastrarPartida(input);
        // opcional: devolve Location: /api/partida/{id}
        URI uri = URI.create("/api/partida/" + criado.getId());
        return ResponseEntity.created(uri).body(criado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PartidaDto> atualizarPartida(
            @PathVariable("id") Long partidaId,
            @Valid @RequestBody PartidaInputDto inDto) {
        PartidaDto atualizado = partidaService.editarPartida(partidaId, inDto);
        return ResponseEntity.ok(atualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPartida(
            @PathVariable("id") Long partidaId) {
        partidaService.removerPartida(partidaId);
        // 204 No Content
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Map<String,Object>> listarComMap(
            @RequestParam(required = false) Long mandante,
            @RequestParam(required = false) Long visitante,
            @RequestParam(required = false) Long estadio,
            Pageable pageable) {

        Page<PartidaDto> page = partidaService.listarPartidas(mandante, visitante, estadio, pageable);

        Map<String,Object> resp = new LinkedHashMap<>();
        resp.put("content",        page.getContent());
        resp.put("pageNumber",     page.getNumber());
        resp.put("pageSize",       page.getSize());
        resp.put("totalElements",  page.getTotalElements());
        resp.put("totalPages",     page.getTotalPages());
        resp.put("last",           page.isLast());

        return ResponseEntity.ok(resp);
    }
}
