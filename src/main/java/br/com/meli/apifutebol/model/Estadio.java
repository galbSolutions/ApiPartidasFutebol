package br.com.meli.apifutebol.model;

import jakarta.persistence.*;

@Table(
        name    = "estadio"

)
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name             = "id",
            nullable         = false,
            columnDefinition = "BIGINT UNSIGNED"
    )
    private Long id;

    @Column(
            name    = "nome",
            length  = 100,
            nullable= false
    )
    private String nome;

    @Column(
            name    = "cidade",
            length  = 100,
            nullable= false
    )
    private String cidade;

    @Column(
            name             = "capacidade",
            nullable         = false,
            columnDefinition = "INT UNSIGNED"
    )
    private Integer capacidade;

    @Column(
            name             = "status_",
            nullable         = false,
            columnDefinition = "TINYINT(1) DEFAULT 1"
    )
    private boolean status = true;

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
