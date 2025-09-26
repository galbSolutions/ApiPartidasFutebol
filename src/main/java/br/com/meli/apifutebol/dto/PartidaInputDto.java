package br.com.meli.apifutebol.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PartidaInputDto {
    @NotNull
    private Long mandanteId;

    @NotNull
    private Long visitanteId;

    @NotNull
    private Long estadioId;

    @NotNull
    private String resultado;

    @NotNull
    private LocalDateTime dataHora;

    // getters + setters
    public Long getMandanteId() { return mandanteId; }
    public void setMandanteId(Long mandanteId) { this.mandanteId = mandanteId; }

    public Long getVisitanteId() { return visitanteId; }
    public void setVisitanteId(Long visitanteId) { this.visitanteId = visitanteId; }

    public Long getEstadioId() { return estadioId; }
    public void setEstadioId(Long estadioId) { this.estadioId = estadioId; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
