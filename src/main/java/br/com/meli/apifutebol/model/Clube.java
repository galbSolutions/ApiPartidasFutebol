package br.com.meli.apifutebol.model;

import br.com.meli.apifutebol.utils.Enum;
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

    @Enumerated(EnumType.STRING)//instrui o JPA a ler/gravar o nome do enum (por exemplo “SP”) na coluna VARCHAR(2) ao invés de usar o ordinal (0,1,2…), que geraria esse conflito de tipo Byte.
    @Column(name = "estado_sede", nullable = false, length = 2)
    private Enum.UF estadoSede;

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

    public Enum.UF getEstadoSede() {
        return estadoSede;
    }

    public void setEstadoSede(Enum.UF estadoSede) {
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
