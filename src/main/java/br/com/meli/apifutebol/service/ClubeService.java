package br.com.meli.apifutebol.service;

import br.com.meli.apifutebol.dto.ClubeDto;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.repository.ClubeRepository;
import br.com.meli.apifutebol.utils.Enum;
import br.com.meli.apifutebol.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClubeService {
    @Autowired
    private final ClubeRepository clubeRepository;
    public ClubeService(ClubeRepository clubeRepository) {
        this.clubeRepository = clubeRepository;
    }

    public List<ClubeDto> getClube (){
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
        }catch (Exception ex){
            log.error("Erro ao buscar clubes", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filtro inválido",ex);
        }
    }

    public ClubeDto insertClube(ClubeDto dto){
        ClubeDto resp = new ClubeDto();
        StringUtil valida = new StringUtil();
        Clube c = new Clube();
        try{
            boolean name = valida.isLengthBetween(dto.getClubName(),3,100);
            if(name== true){
                c.setNomeClube(dto.getClubName());
            }
            boolean date = valida.checkDate(dto.getCreatAt());
            if(date==true){
                c.setDataCriacao(dto.getCreatAt());
            }
            boolean uf = valida.isUFValid(dto.getUf().toString());
            if(uf == true){
                c.setEstadoSede(dto.getUf());
            }
            Clube insert = clubeRepository.save(c);
            resp = new ClubeDto(
                    insert.getId(),
                    insert.getNomeClube(),
                    insert.getEstadoSede(),
                    insert.getDataCriacao().toString(),
                    insert.isStatus()
            );
        }catch (Exception ex){
            log.error("Erro ao inserir Clubes", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao inserir",ex);
        }
        return resp;
    }
    //Fica explícito na assinatura do método que retorno pode estar ausente
    public Optional<ClubeDto> getClubeById(long id){
        return clubeRepository.findById(id)
                .map(entity -> new ClubeDto(
                        entity.getId(),
                        entity.getNomeClube(),
                        entity.getEstadoSede(),
                        entity.getDataCriacao().toString(),
                        entity.isStatus()
                ));

    }

    public Optional<ClubeDto> validaClube(long id){
        try{

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

        }catch (Exception ex){
            throw new IllegalArgumentException();
        }
    }
    public Page<ClubeDto> listar(
            String nome,
            String estadoStr,
            Boolean ativo,
            Pageable pageable
    ) {
        // prepara nome
        String nomeParam = (nome == null || nome.isBlank())
                ? null
                : nome.trim();

        // converte estadoStr em Enum.UF ou null
        Enum.UF estadoParam = null;
        if (estadoStr != null && !estadoStr.isBlank()) {
            estadoParam = Enum.UF.valueOf(estadoStr.toUpperCase());
        }

        // executa a query passando o enum
        Page<Clube> page = clubeRepository.findByFiltro(
                nomeParam,    // será usado no LIKE
                estadoParam,  // passa o Enum, não String
                ativo,
                pageable
        );

        // mapeia para DTO
        return page.map(c ->
                new ClubeDto(
                        c.getId(),
                        c.getNomeClube(),
                        c.getEstadoSede(),
                        c.getDataCriacao().toString(),
                        c.isStatus()
                )
        );
    }

    }

