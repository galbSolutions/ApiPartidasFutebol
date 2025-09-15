package br.com.meli.apifutebol.service;

import br.com.meli.apifutebol.dto.ClubeDto;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.repository.ClubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
            return Collections.emptyList();
        }
    }

    public ClubeDto insertClube(ClubeDto dto){
        Clube c = new Clube();
        c.setNomeClube(dto.getClubName());
        c.setEstadoSede(dto.getUf());
        c.setDataCriacao(dto.getCreatAt());
        Clube insert = clubeRepository.save(c);
        ClubeDto resp = new ClubeDto(
                insert.getId(),
                insert.getNomeClube(),
                insert.getEstadoSede(),
                insert.getDataCriacao().toString(),
                insert.isStatus()
        );
        return resp;

    }
}
