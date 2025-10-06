package br.com.meli.apifutebol.dto;

public class AdversarioRetrospectoDto {
    private Long adversarioId;
    private String adversarioNome;
    private long vitorias;
    private long empates;
    private long derrotas;
    private int golsFeitos;
    private int golsSofridos;

    public AdversarioRetrospectoDto(Long adversarioId,
                      String adversarioNome,
                      long vitorias,
                      long empates,
                      long derrotas,
                      int golsFeitos,
                      int golsSofridos) {
        this.adversarioId   = adversarioId;
        this.adversarioNome = adversarioNome;
        this.vitorias       = vitorias;
        this.empates        = empates;
        this.derrotas       = derrotas;
        this.golsFeitos     = golsFeitos;
        this.golsSofridos   = golsSofridos;
    }
    public Long getAdversarioId() {
        return adversarioId;
    }

    public void setAdversarioId(Long adversarioId) {
        this.adversarioId = adversarioId;
    }

    public String getAdversarioNome() {
        return adversarioNome;
    }

    public void setAdversarioNome(String adversarioNome) {
        this.adversarioNome = adversarioNome;
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
