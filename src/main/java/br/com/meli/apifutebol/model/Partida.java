package br.com.meli.apifutebol.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(
        name = "partida",
        indexes = {
                @Index(name = "idx_partida_mandante", columnList = "clube_mandante_id"),
                @Index(name = "idx_partida_visitante", columnList = "clube_visitante_id")
        }
)
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            nullable = false,
            columnDefinition = "BIGINT UNSIGNED"
    )
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "clube_mandante_id",
            nullable = false,
            columnDefinition = "BIGINT UNSIGNED",
            foreignKey = @ForeignKey(
                    name = "partida_ibfk_1",
                    foreignKeyDefinition =
                            "FOREIGN KEY (clube_mandante_id) " +
                                    "REFERENCES clube(id) " +
                                    "ON UPDATE CASCADE " +
                                    "ON DELETE RESTRICT"
            )
    )
    private Clube clubeMandante;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "clube_visitante_id",
            nullable = false,
            columnDefinition = "BIGINT UNSIGNED",
            foreignKey = @ForeignKey(
                    name = "partida_ibfk_2",
                    foreignKeyDefinition =
                            "FOREIGN KEY (clube_visitante_id) " +
                                    "REFERENCES clube(id) " +
                                    "ON UPDATE CASCADE " +
                                    "ON DELETE RESTRICT"
            )
    )
    private Clube clubeVisitante;

    @Column(
            name = "resultado",
            length = 10,
            nullable = false,
            columnDefinition = "VARCHAR(10) COMMENT 'formato “x1x2”'"
    )
    private String resultado;

    @Column(
            name = "estadio",
            length = 100,
            nullable = false,
            columnDefinition = "VARCHAR(100)"
    )
    private String estadio;

    @Column(
            name = "data_hora",
            nullable = false,
            columnDefinition = "DATETIME"
    )
    private LocalDateTime dataHora;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clube getClubeMandante() {
        return clubeMandante;
    }

    public void setClubeMandante(Clube clubeMandante) {
        this.clubeMandante = clubeMandante;
    }

    public Clube getClubeVisitante() {
        return clubeVisitante;
    }

    public void setClubeVisitante(Clube clubeVisitante) {
        this.clubeVisitante = clubeVisitante;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
