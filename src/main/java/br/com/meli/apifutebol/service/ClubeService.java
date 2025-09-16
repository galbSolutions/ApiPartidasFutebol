package br.com.meli.apifutebol.service;

import br.com.meli.apifutebol.dto.ClubeDto;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.repository.ClubeRepository;
import br.com.meli.apifutebol.utils.StringUtil;
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

        }

        return resp;

    }
}
