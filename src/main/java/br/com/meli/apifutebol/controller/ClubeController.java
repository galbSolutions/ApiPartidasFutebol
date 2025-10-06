package br.com.meli.apifutebol.controller;

import br.com.meli.apifutebol.dto.*;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.service.ClubeService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/clube")
public class ClubeController {

    private final ClubeService clubeService;
    public ClubeController(ClubeService clubeService) {
        this.clubeService = clubeService;
    }

    @GetMapping
    public ResponseEntity<List<ClubeDto>> getClube(){
        List<ClubeDto> clubes = clubeService.getClube();
        return ResponseEntity.ok(clubes);
    }

    @PostMapping()
    public ResponseEntity<RespDto> insertClube(@Valid @RequestBody ClubeDto dto) {
        clubeService.insertClube(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping(params = "id")
    public ResponseEntity<ClubeDto> getClubById(@RequestParam("id") long id){
        Optional<ClubeDto> resp = clubeService.getClubeById(id);
        if(resp.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resp.get());
    }
    @DeleteMapping(params = "id")
    public ResponseEntity<ClubeDto>  inativeClube(@RequestParam ("id") long id){
        Optional<ClubeDto> resp = clubeService.validaClube(id);
        if(resp.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resp.get());

    }
    //Método opcional
    @PutMapping(params = "id")
    public ResponseEntity<ClubeDto>  ativaClube(@RequestParam ("id") long id){
        Optional<ClubeDto> resp = clubeService.validaClube(id);
        if(resp.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resp.get());

    }

    @GetMapping("/filter")
    public ResponseEntity<Map<String,Object>> filter(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(page = 0, size = 20,
                    sort = "nomeClube", direction = Sort.Direction.ASC)
                    Pageable pageable
    ) {
        // busca paginada
        var page = clubeService.listar(nome, estado, ativo, pageable);

        // monta só os campos que interessam
        Map<String,Object> resp = new LinkedHashMap<>();
        resp.put("content",       page.getContent());
        resp.put("pageNumber",    page.getNumber());
        resp.put("pageSize",      page.getSize());
        resp.put("totalElements", page.getTotalElements());
        resp.put("totalPages",    page.getTotalPages());
        resp.put("last",          page.isLast());

        return ResponseEntity.ok(resp);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClubeDto> updateClube(
            @PathVariable Long id,
            @RequestBody @Valid ClubeUpdateDto dto
    ) {
        ClubeDto atualizado = clubeService.updateClube(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/{id}/retrospecto")
    public ResponseEntity<RetrospectoDto> getRetrospecto(
            @PathVariable("id") Long clubeId) {
        RetrospectoDto dto = clubeService.getRetrospecto(clubeId);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/{idA}/confronto/{idB}")
    public ResponseEntity<ConfrontoDto> confronto(
            @PathVariable Long idA,
            @PathVariable Long idB
    ) {
        ConfrontoDto dto = clubeService.getConfrontoDireto(idA, idB);
        return ResponseEntity.ok(dto);
    }


}
