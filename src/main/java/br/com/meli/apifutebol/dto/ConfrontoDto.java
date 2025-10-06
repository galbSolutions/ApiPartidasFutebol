package br.com.meli.apifutebol.dto;

import java.util.List;

public class ConfrontoDto {
    private Long clubeAId;
    private Long clubeBId;
    private RetrospectoDto retroA;
    private RetrospectoDto retroB;
    private List<PartidaDto> partidas;

    public ConfrontoDto(Long clubeAId,
                        Long clubeBId,
                        RetrospectoDto retroA,
                        RetrospectoDto retroB,
                        List<PartidaDto> partidas) {
        this.clubeAId  = clubeAId;
        this.clubeBId  = clubeBId;
        this.retroA    = retroA;
        this.retroB    = retroB;
        this.partidas  = partidas;
    }

    public Long getClubeAId() {
        return clubeAId;
    }

    public void setClubeAId(Long clubeAId) {
        this.clubeAId = clubeAId;
    }

    public Long getClubeBId() {
        return clubeBId;
    }

    public void setClubeBId(Long clubeBId) {
        this.clubeBId = clubeBId;
    }

    public RetrospectoDto getRetroA() {
        return retroA;
    }

    public void setRetroA(RetrospectoDto retroA) {
        this.retroA = retroA;
    }

    public RetrospectoDto getRetroB() {
        return retroB;
    }

    public void setRetroB(RetrospectoDto retroB) {
        this.retroB = retroB;
    }

    public List<PartidaDto> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<PartidaDto> partidas) {
        this.partidas = partidas;
    }
}
