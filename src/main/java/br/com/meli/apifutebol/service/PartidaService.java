package br.com.meli.apifutebol.service;


import br.com.meli.apifutebol.dto.PartidaDto;
import br.com.meli.apifutebol.dto.PartidaInputDto;
import br.com.meli.apifutebol.exception.ApiException;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.model.Estadio;
import br.com.meli.apifutebol.model.Partida;
import br.com.meli.apifutebol.repository.ClubeRepository;
import br.com.meli.apifutebol.repository.EstadioRepository;
import br.com.meli.apifutebol.repository.PartidaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        // 1) Dois clubes não podem ser iguais → 400
        if (in.getMandanteId().equals(in.getVisitanteId())) {
            throw ApiException.badRequestGenerico(
                    "Mandante e visitante devem ser clubes diferentes",
                    null
            );
        }
        // 2) Buscar mandante e visitante ou 400 Bad Request
        Clube mandante = clubeRepo.findById(in.getMandanteId())
                .orElseThrow(() ->
                        ApiException.badRequestGenerico(
                                "Clube mandante não encontrado: " + in.getMandanteId(),
                                null
                        )
                );
        Clube visitante = clubeRepo.findById(in.getVisitanteId())
                .orElseThrow(() ->
                        ApiException.badRequestGenerico(
                                "Clube visitante não encontrado: " + in.getVisitanteId(),
                                null
                        )
                );
        // 3) Buscar estádio ou 400
        Estadio estadio = estadioRepo.findById(in.getEstadioId())
                .orElseThrow(() ->
                        ApiException.badRequestGenerico(
                                "Estádio não encontrado: " + in.getEstadioId(),
                                null
                        )
                );
        // 4) Nenhum gol pode ser negativo → 400
        if (in.getGolsMandante() < 0
                || in.getGolsVisitante() < 0) {
            throw ApiException.badRequestGenerico(
                    "Número de gols não pode ser negativo",
                    null
            );
        }
        // 5) Data/hora da partida não pode estar no futuro → 400
        if (in.getDataHora().isAfter(LocalDateTime.now())) {
            throw ApiException.badRequestGenerico(
                    "Data e hora da partida não podem ser no futuro",
                    null
            );
        }
        // 6) Data/hora da partida não pode ser ANTES da criação de nenhum dos clubes → 409
        if (in.getDataHora().isBefore(mandante.getDataCriacao().atStartOfDay())
                || in.getDataHora().isBefore(visitante.getDataCriacao().atStartOfDay())) {
            throw ApiException.dataCriacaoConflito();
        }
        // 7) Clubes envolvidos devem estar ativos → 409
        if (!mandante.isStatus()) {
            throw ApiException.clubeInativo(mandante.getId());
        }
        if (!visitante.isStatus()) {
            throw ApiException.clubeInativo(visitante.getId());
        }

        // 8) Não pode marcar outra partida para o mesmo clube em intervalo < 48h → 409
        LocalDateTime inicio48h = in.getDataHora().minusHours(48);
        LocalDateTime fim48h    = in.getDataHora().plusHours(48);
        boolean proximas = partidaRepository
                .existsByClubeMandanteAndDataHoraBetween(mandante, inicio48h, fim48h)
                || partidaRepository
                .existsByClubeVisitanteAndDataHoraBetween(mandante, inicio48h, fim48h)
                || partidaRepository
                .existsByClubeMandanteAndDataHoraBetween(visitante, inicio48h, fim48h)
                || partidaRepository
                .existsByClubeVisitanteAndDataHoraBetween(visitante, inicio48h, fim48h);

        if (proximas) {
            throw ApiException.conflictGenerico(
                    "Um dos clubes já tem partida em intervalo menor que 48 horas"
            );
        }
        // 9) Não pode haver outra partida no mesmo estádio no mesmo dia → 409
        LocalDateTime diaInicio = in.getDataHora().toLocalDate().atStartOfDay();
        LocalDateTime diaFim    = diaInicio.plusDays(1);
        if (partidaRepository.existsByEstadioAndDataHoraBetween(estadio, diaInicio, diaFim)) {
            throw ApiException.conflictGenerico(
                    "Estádio já possui jogo cadastrado neste dia"
            );
        }
        // 10) Tudo validado: monta a entidade e salva
        Partida p = new Partida();
        p.setClubeMandante(mandante);
        p.setClubeVisitante(visitante);
        p.setEstadio(estadio);
        p.setResultado(in.getResultado());
        p.setDataHora(in.getDataHora());
        p.setGolsMandante(in.getGolsMandante());
        p.setGolsVisitante(in.getGolsVisitante());

        Partida salvo = partidaRepository.save(p);
        return new PartidaDto(salvo);
    }

    public PartidaDto editarPartida(Long partidaId, PartidaInputDto in) {
        // 0) Partida deve existir (404)
        Partida existente = partidaRepository.findById(partidaId)
                .orElseThrow(() -> ApiException.partidaNaoEncontrada(partidaId));

        // 1) Dois clubes não podem ser iguais → 400
        if (in.getMandanteId().equals(in.getVisitanteId())) {
            throw ApiException.badRequestGenerico(
                    "Mandante e visitante devem ser clubes diferentes", null
            );
        }

        // 2) Buscar clubes e estádio ou 400
        Clube mandante  = clubeRepo.findById(in.getMandanteId())
                .orElseThrow(() -> ApiException.badRequestGenerico(
                        "Clube mandante não encontrado: " + in.getMandanteId(), null
                ));
        Clube visitante = clubeRepo.findById(in.getVisitanteId())
                .orElseThrow(() -> ApiException.badRequestGenerico(
                        "Clube visitante não encontrado: " + in.getVisitanteId(), null
                ));
        Estadio estadio = estadioRepo.findById(in.getEstadioId())
                .orElseThrow(() -> ApiException.badRequestGenerico(
                        "Estádio não encontrado: " + in.getEstadioId(), null
                ));

        // 3) Gols não podem ser negativos → 400
        if (in.getGolsMandante() < 0 || in.getGolsVisitante() < 0) {
            throw ApiException.badRequestGenerico(
                    "Número de gols não pode ser negativo", null
            );
        }

        // 4) Data não pode estar no futuro → 400
        if (in.getDataHora().isAfter(LocalDateTime.now())) {
            throw ApiException.badRequestGenerico(
                    "Data e hora da partida não podem ser no futuro", null
            );
        }

        // 5) Data anterior à criação de um dos clubes → 409
        if (in.getDataHora().isBefore(mandante.getDataCriacao().atStartOfDay())
                || in.getDataHora().isBefore(visitante.getDataCriacao().atStartOfDay())) {
            throw ApiException.dataCriacaoConflito();
        }

        // 6) Clube inativo → 409
        if (!mandante.isStatus()) {
            throw ApiException.clubeInativo(mandante.getId());
        }
        if (!visitante.isStatus()) {
            throw ApiException.clubeInativo(visitante.getId());
        }

        // 7) Partidas próximas (<48h) → 409 (ignora a própria partida)
        LocalDateTime inicio48h = in.getDataHora().minusHours(48);
        LocalDateTime fim48h    = in.getDataHora().plusHours(48);
        boolean proximas = partidaRepository
                .existsByClubeMandanteAndDataHoraBetweenAndIdNot(mandante, inicio48h, fim48h, partidaId)
                || partidaRepository
                .existsByClubeVisitanteAndDataHoraBetweenAndIdNot(mandante, inicio48h, fim48h, partidaId)
                || partidaRepository
                .existsByClubeMandanteAndDataHoraBetweenAndIdNot(visitante, inicio48h, fim48h, partidaId)
                || partidaRepository
                .existsByClubeVisitanteAndDataHoraBetweenAndIdNot(visitante, inicio48h, fim48h, partidaId);

        if (proximas) {
            throw ApiException.conflictGenerico(
                    "Um dos clubes já tem partida em intervalo menor que 48 horas"
            );
        }

        // 8) Estádio ocupado no mesmo dia → 409 (ignora a própria partida)
        LocalDateTime diaInicio = in.getDataHora().toLocalDate().atStartOfDay();
        LocalDateTime diaFim    = diaInicio.plusDays(1);
        if (partidaRepository.existsByEstadioAndDataHoraBetweenAndIdNot(
                estadio, diaInicio, diaFim, partidaId)) {
            throw ApiException.conflictGenerico(
                    "Estádio já possui jogo cadastrado neste dia"
            );
        }

        // 9) Tudo validado: atualiza e salva
        existente.setClubeMandante(mandante);
        existente.setClubeVisitante(visitante);
        existente.setEstadio(estadio);
        existente.setGolsMandante(in.getGolsMandante());
        existente.setGolsVisitante(in.getGolsVisitante());
        existente.setDataHora(in.getDataHora());
        // se usar resultado:String
        existente.setResultado(in.getResultado());

        Partida atualizado = partidaRepository.save(existente);
        return new PartidaDto(atualizado);
    }

    public void removerPartida(Long partidaId) {
        // 1) Verifica existência (404 caso não exista)
        Partida p = partidaRepository.findById(partidaId)
                .orElseThrow(() -> ApiException.partidaNaoEncontrada(partidaId));
        // 2) Remove de vez (hard delete)
        partidaRepository.delete(p);
    }

    public Page<PartidaDto> listarPartidas(
            Long mandanteId,
            Long visitanteId,
            Long estadioId,
            Pageable pageable
    ) {
        // 1) normaliza filtros: só aceita >0, senão null
        Long manParam = (mandanteId != null && mandanteId > 0) ? mandanteId : null;
        Long visParam = (visitanteId != null && visitanteId > 0) ? visitanteId : null;
        Long estParam = (estadioId   != null && estadioId   > 0) ? estadioId   : null;

        // 2) monta o probe e lista campos que sempre ignoraremos
        Partida probe = new Partida();
        List<String> ignore = new ArrayList<>(List.of(
                "id",
                "dataHora",
                "golsMandante",
                "golsVisitante"
        ));
        // 3) para cada filtro, setamos ou ignoramos
        if (manParam != null) {
            Clube cm = new Clube();
            cm.setId(manParam);
            probe.setClubeMandante(cm);
        } else {
            ignore.add("clubeMandante");
        }
        if (visParam != null) {
            Clube cv = new Clube();      cv.setId(visParam);
            probe.setClubeVisitante(cv);
        } else {
            ignore.add("clubeVisitante");
        }
        if (estParam != null) {
            Estadio e = new Estadio();   e.setId(estParam);
            probe.setEstadio(e);
        } else {
            ignore.add("estadio");
        }

        // 4) configura o matcher para ignorar nulls e campos listados
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnorePaths(ignore.toArray(new String[0]))
                .withIgnoreCase();  // comparações case-insensitive (usamos default Equals para IDs)

        // 5) monta e executa a query paginada
        Example<Partida> example = Example.of(probe, matcher);
        Page<Partida> pageEnt = partidaRepository.findAll(example, pageable);

        // 6) mapeia para DTO
        return pageEnt.map(PartidaDto::new);
    }
}


