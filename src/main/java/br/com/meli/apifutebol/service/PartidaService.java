package br.com.meli.apifutebol.service;


import br.com.meli.apifutebol.dto.PartidaDto;
import br.com.meli.apifutebol.dto.PartidaInputDto;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.model.Estadio;
import br.com.meli.apifutebol.model.Partida;
import br.com.meli.apifutebol.repository.ClubeRepository;
import br.com.meli.apifutebol.repository.EstadioRepository;
import br.com.meli.apifutebol.repository.PartidaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final ClubeRepository clubeRepo;
    private final EstadioRepository estadioRepo;
    public PartidaService(PartidaRepository partidaRepository,ClubeRepository clubeRepo,EstadioRepository estadioRepo)
    {this.partidaRepository = partidaRepository;
     this.clubeRepo = clubeRepo;
     this.estadioRepo = estadioRepo;
    }

    public Page<PartidaDto> listarPartidas(Pageable pageable) {
        return partidaRepository.findAll(pageable)
                .map(PartidaDto::new);
    }
    public PartidaDto cadastrarPartida(PartidaInputDto in) {
        // Busca mandante ou 404
        Clube mandante = clubeRepo.findById(in.getMandanteId())
                .orElseThrow(()->
                        new RuntimeException(
                                "Clube mandante não encontrado: " + in.getMandanteId()));

        // Busca visitante ou 404
        Clube visitante = clubeRepo.findById(in.getVisitanteId())
                .orElseThrow(()->
                        new RuntimeException("Clube visitante não encontrado: " + in.getVisitanteId()));

        // Busca estádio ou 404
        Estadio estadio = estadioRepo.findById(in.getEstadioId())
                .orElseThrow(()->
                        new RuntimeException(
                                "Estádio não encontrado: " + in.getEstadioId()));

        // Cria entidade
        Partida p = new Partida();
        p.setClubeMandante(mandante);
        p.setClubeVisitante(visitante);
        p.setEstadio(estadio);
        p.setResultado(in.getResultado());
        p.setDataHora(in.getDataHora());

        // Salva
        Partida salvo = partidaRepository.save(p);

        // Converte e retorna DTO
        return new PartidaDto(salvo);
    }

}
