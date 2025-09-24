package br.com.meli.apifutebol.dto;

public class EstadioDto {
    private Long id;
    private String nome;
    private String cidade;
    private Integer capacidade;
    private boolean status;

    // Construtor padrão

    public EstadioDto(){}

    // Construtor com todos os campos
    public EstadioDto(Long id,
                      String nome,
                      String cidade,
                      Integer capacidade,
                      boolean status) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.status = status;
    }


    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
