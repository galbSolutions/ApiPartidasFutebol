package br.com.meli.apifutebol.controller;

import br.com.meli.apifutebol.dto.ClubeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/clube")
public class ClubeController {

    @GetMapping
    public ClubeDto getClube(){
        ClubeDto c = new ClubeDto();
        c.clubName = "São Paulo";
        c.uf = "SP";
        c.creatAt = "25/01/1930";
        c.status = true;
        return c;
    }
}
