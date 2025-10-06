package br.com.meli.apifutebol.dto;

public class RetrospectoDto {
    private Long clubeId;
    private long vitorias;
    private long empates;
    private long derrotas;
    private int  golsFeitos;
    private int  golsSofridos;

    public RetrospectoDto(Long clubeId, long vitorias, long empates,
                          long derrotas, int golsFeitos, int golsSofridos) {
        this.clubeId      = clubeId;
        this.vitorias     = vitorias;
        this.empates      = empates;
        this.derrotas     = derrotas;
        this.golsFeitos   = golsFeitos;
        this.golsSofridos = golsSofridos;
    }

    public Long getClubeId() {
        return clubeId;
    }

    public void setClubeId(Long clubeId) {
        this.clubeId = clubeId;
    }

    public long getVitorias() {
        return vitorias;
    }

    public void setVitorias(long vitorias) {
        this.vitorias = vitorias;
    }

    public long getEmpates() {
        return empates;
    }

    public void setEmpates(long empates) {
        this.empates = empates;
    }

    public long getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(long derrotas) {
        this.derrotas = derrotas;
    }

    public int getGolsFeitos() {
        return golsFeitos;
    }

    public void setGolsFeitos(int golsFeitos) {
        this.golsFeitos = golsFeitos;
    }

    public int getGolsSofridos() {
        return golsSofridos;
    }

    public void setGolsSofridos(int golsSofridos) {
        this.golsSofridos = golsSofridos;
    }
}
