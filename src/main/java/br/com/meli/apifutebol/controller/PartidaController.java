package br.com.meli.apifutebol.controller;

import br.com.meli.apifutebol.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/partida")
public class PartidaController {
    @Autowired
    private PartidaService partidaRepository;


}
