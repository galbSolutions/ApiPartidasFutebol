package br.com.meli.apifutebol.service;

import br.com.meli.apifutebol.dto.ClubeDto;
import br.com.meli.apifutebol.dto.ClubeUpdateDto;
import br.com.meli.apifutebol.exception.ApiException;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.model.Partida;
import br.com.meli.apifutebol.repository.ClubeRepository;
import br.com.meli.apifutebol.repository.PartidaRepository;
import br.com.meli.apifutebol.utils.Enum;
import br.com.meli.apifutebol.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClubeService {
    @Autowired
    private final ClubeRepository clubeRepository;
    private final PartidaRepository partidaRepository;

    public ClubeService(ClubeRepository clubeRepository, PartidaRepository partidaRepository) {
        this.clubeRepository = clubeRepository;
        this.partidaRepository = partidaRepository;
    }

    public List<ClubeDto> getClube() {
        try {
            return clubeRepository.findAll().stream()
                    // para cada entidade Clube → cria um ClubeDto
                    .map(resp -> new ClubeDto(
                            resp.getId(),
                            resp.getNomeClube(),
                            resp.getEstadoSede(),
                            resp.getDataCriacao().toString(),
                            resp.isStatus()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("Erro ao buscar clubes", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filtro inválido", ex);
        }
    }

    public ClubeDto insertClube(ClubeDto dto) {
        ClubeDto resp = new ClubeDto();
        StringUtil valida = new StringUtil();
        Clube c = new Clube();
        try {
            boolean name = valida.isLengthBetween(dto.getClubName(), 3, 100);
            if (!name) {
                throw ApiException.badRequestGenerico(
                        "Nome do clube é menor do que 2 letras.",
                        new IllegalArgumentException(""));
            }
            c.setNomeClube(dto.getClubName());
            boolean date = valida.checkDate(dto.getCreatAt());
            if (!date) {
                throw ApiException.badRequestGenerico(
                        "Data de criação inválida ou no futuro.",
                        new IllegalArgumentException(""));
            }
            c.setDataCriacao(dto.getCreatAt());

            if (!valida.isUFValid(dto.getUf().toString())) {
                throw ApiException.badRequestGenerico(
                        "UF inválida.",
                        new IllegalArgumentException("uf não consta nos estados válidos")
                );
            }
            c.setEstadoSede(dto.getUf());
            // Validar duplicidade de Clube + estado CONFLICT
            Optional<Clube> exists = clubeRepository
                    .findByNomeClubeAndEstadoSede(
                            dto.getClubName(),
                            dto.getUf()
                    );
            if (exists.isPresent()) {
                throw ApiException.nomeEstadoDuplicado(dto.getClubName(), dto.getUf());
            }

            Clube insert = clubeRepository.save(c);
            resp = new ClubeDto(
                    insert.getId(),
                    insert.getNomeClube(),
                    insert.getEstadoSede(),
                    insert.getDataCriacao().toString(),
                    insert.isStatus()
            );
        }catch (ResponseStatusException ex){
            throw ex;
        }
        catch (Exception ex) {
            log.error("ERROR: ", ex);
            throw ApiException.badRequestGenerico("BAD REQUEST dados inválidos",ex);
        }
        return resp;
    }

    //Fica explícito na assinatura do método que retorno pode estar ausente
    public Optional<ClubeDto> getClubeById(long id) {
        return clubeRepository.findById(id)
                .map(entity -> new ClubeDto(
                        entity.getId(),
                        entity.getNomeClube(),
                        entity.getEstadoSede(),
                        entity.getDataCriacao().toString(),
                        entity.isStatus()
                ));

    }

    public Optional<ClubeDto> validaClube(long id) {
        try {

            Clube clube = clubeRepository.findById(id)
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube não encontrado"));
            // marca como inativo e salva
            clube.setStatus(!clube.isStatus());
            clubeRepository.save(clube);
            return clubeRepository.findById(id)
                    .map(entity -> new ClubeDto(
                            entity.getId(),
                            entity.getNomeClube(),
                            entity.getEstadoSede(),
                            entity.getDataCriacao().toString(),
                            entity.isStatus()
                    ));

        }catch (ResponseStatusException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException();
        }
    }

    public Page<ClubeDto> listar(
            String nome,
            String estadoStr,
            Boolean ativo,
            Pageable pageable
    ) {
        // 1) Normaliza filtros
        String nomeParam = (nome   != null && !nome.isBlank())
                ? nome.trim() : null;

        Enum.UF estadoParam = null;
        if (estadoStr != null && !estadoStr.isBlank()) {
            try {
                estadoParam = Enum.UF.valueOf(estadoStr.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "UF inválida. Use: " + Arrays.toString(Enum.UF.values()),
                        ex
                );
            }
        }
        // 2) Constrói o probe só com os filtros efetivos
        Clube probe = new Clube();
        List<String> ignore = new ArrayList<>();
        ignore.add("id");
        ignore.add("dataCriacao");
        if (nomeParam != null) {
            probe.setNomeClube(nomeParam);
        } else {
            ignore.add("nomeClube");
        }
        if (estadoParam != null) {
            probe.setEstadoSede(estadoParam);
        } else {
            ignore.add("estadoSede");
        }
        if (ativo != null) {
            probe.setStatus(ativo);
        } else {
            // como status é boolean primitivo, se não setarmos
            // ele ficará false por default → precisamos ignorá-lo
            ignore.add("status");
        }
        // 3) Configura o matcher para ignorar campos nulos e paths específicos
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()                   // ignora campos null
                .withIgnorePaths(ignore.toArray(new String[0]))
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Clube> example = Example.of(probe, matcher);

        // 4) Executa a query paginada/ordenada
        Page<Clube> pageEnt = clubeRepository.findAll(example, pageable);

        // 5) Mapeia para ClubeDto e retorna
        return pageEnt.map(c ->
                new ClubeDto(
                        c.getId(),
                        c.getNomeClube(),
                        c.getEstadoSede(),
                        c.getDataCriacao().toString(),
                        c.isStatus()
                )
        );
    }

    @Transactional
    public ClubeDto updateClube(Long id, ClubeUpdateDto dto) {
        try {
            // Clube existe?
            Clube clube = clubeRepository.findById(id)
                    .orElseThrow(() -> ApiException.clubeNaoEncontrado(id));

            //  Data de criação não no futuro? (util)
            if (!StringUtil.checkDate(dto.getDataCriacao())) {
                throw ApiException.dataCriacaoFutura();
            }

            // ■ Data de criação não pode ser posterior a partidas já registradas
            LocalDate novaData = dto.getDataCriacao();
            List<Partida> partidasDoClube =
                    partidaRepository.findByClubeMandante_IdOrClubeVisitante_Id(id, id);
            boolean temConflitoData = partidasDoClube.stream()
                    .anyMatch(p -> p.getDataHora().toLocalDate().isBefore(novaData));
            if (temConflitoData) {
                throw ApiException.dataCriacaoConflito();
            }

            // Conflito de nome no mesmo estado? (409)
            Optional<Clube> outro = clubeRepository
                    .findByNomeClubeAndEstadoSede(dto.getNomeClube(), dto.getEstadoSede());
            if (outro.isPresent()
                    && !id.equals(outro.get().getId())) {
                throw ApiException.nomeEstadoDuplicado(dto.getNomeClube(), dto.getEstadoSede());
            }

            // ■ Tudo ok, faz update
            clube.setNomeClube(dto.getNomeClube());
            clube.setEstadoSede(dto.getEstadoSede());
            clube.setDataCriacao(novaData);

            Clube salvo = clubeRepository.save(clube);
            return new ClubeDto(
                    salvo.getId(),
                    salvo.getNomeClube(),
                    salvo.getEstadoSede(),
                    salvo.getDataCriacao().toString(),
                    salvo.isStatus()
            );

        } catch (ResponseStatusException ex) {
            // relança 404, 400 ou 409 já definidos nos ApiException
            throw ex;
        } catch (DataAccessException dae) {
            log.error("Falha de acesso a dados ao atualizar clube {}", id, dae);
            throw ApiException.erroBancoDados(id, dae);
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar clube {}", id, ex);
            throw ApiException.badRequestGenerico(
                    "Dados inválidos para atualização de clube.", ex
            );
        }
    }
}

