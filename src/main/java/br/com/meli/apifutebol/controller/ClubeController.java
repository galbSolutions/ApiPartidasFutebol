package br.com.meli.apifutebol.controller;

import br.com.meli.apifutebol.dto.ClubeDto;
import br.com.meli.apifutebol.dto.RespDto;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.service.ClubeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        ClubeDto clubes = new ClubeDto();
        RespDto resp = new RespDto();
        try {
             clubes = clubeService.insertClube(dto);
            if (clubes.getId() != 0) {
                resp.message = "Cadastramento de Clube";
                resp.success = true;

            } else {
                resp.message = "Cadastramento de Clube";
                resp.success = false;
            }
        } catch (Exception ex) {
            resp.message = "Erro interno ";
            resp.success = false;
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
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
    public Page<ClubeDto> filter(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Boolean ativo,
            Pageable pageable
    ) {
        return clubeService.listar(nome, estado, ativo, pageable);
    }


}
