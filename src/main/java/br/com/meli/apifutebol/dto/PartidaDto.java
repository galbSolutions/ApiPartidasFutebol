package br.com.meli.apifutebol.dto;

import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.model.Estadio;
import br.com.meli.apifutebol.model.Partida;

import java.time.LocalDateTime;

public class PartidaDto {

    private Long   id;
    private Long   mandanteId;
    private String mandanteNome;
    private Long   visitanteId;
    private String visitanteNome;
    private Long   estadioId;
    private String estadioNome;
    private String resultado;
    private LocalDateTime dataHora;

    public PartidaDto(){}
    public PartidaDto(
            Long id,
            Long mandanteId, String mandanteNome,
            Long visitanteId, String visitanteNome,
            Long estadioId,  String estadioNome,
            String resultado,
            LocalDateTime dataHora) {
        this.id             = id;
        this.mandanteId     = mandanteId;
        this.mandanteNome   = mandanteNome;
        this.visitanteId    = visitanteId;
        this.visitanteNome  = visitanteNome;
        this.estadioId      = estadioId;
        this.estadioNome    = estadioNome;
        this.resultado      = resultado;
        this.dataHora       = dataHora;
    }

    public PartidaDto(Partida p) {
        this.id             = p.getId();
        this.mandanteId     = p.getClubeMandante().getId();
        this.mandanteNome   = p.getClubeMandante().getNomeClube();
        this.visitanteId    = p.getClubeVisitante().getId();
        this.visitanteNome  = p.getClubeVisitante().getNomeClube();
        this.estadioId      = p.getEstadio().getId();
        this.estadioNome    = p.getEstadio().getNome();
        this.resultado      = p.getResultado();
        this.dataHora       = p.getDataHora();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getMandanteId() {
        return mandanteId;
    }
    public void setMandanteId(Long mandanteId) {
        this.mandanteId = mandanteId;
    }

    public String getMandanteNome() {
        return mandanteNome;
    }
    public void setMandanteNome(String mandanteNome) {
        this.mandanteNome = mandanteNome;
    }

    public Long getVisitanteId() {
        return visitanteId;
    }
    public void setVisitanteId(Long visitanteId) {
        this.visitanteId = visitanteId;
    }

    public String getVisitanteNome() {
        return visitanteNome;
    }
    public void setVisitanteNome(String visitanteNome) {
        this.visitanteNome = visitanteNome;
    }

    public Long getEstadioId() {
        return estadioId;
    }
    public void setEstadioId(Long estadioId) {
        this.estadioId = estadioId;
    }

    public String getEstadioNome() {
        return estadioNome;
    }
    public void setEstadioNome(String estadioNome) {
        this.estadioNome = estadioNome;
    }

    public String getResultado() {
        return resultado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }


}
