package br.com.meli.apifutebol.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "clube",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_nme_clube",
                columnNames = "nome_clube"
        )
)
public class Clube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome_clube", nullable = false, length = 100)
    private String nomeClube;

    @Column(name = "estado_sede", nullable = false, length = 2)
    private String estadoSede;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name = "status_")

    // Getters and Setters
    private boolean status = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeClube() {
        return nomeClube;
    }

    public void setNomeClube(String nomeClube) {
        this.nomeClube = nomeClube;
    }

    public String getEstadoSede() {
        return estadoSede;
    }

    public void setEstadoSede(String estadoSede) {
        this.estadoSede = estadoSede;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
