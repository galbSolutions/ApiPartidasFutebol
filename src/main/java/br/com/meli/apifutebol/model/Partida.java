package br.com.meli.apifutebol.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name    = "partida",
        indexes = {
                @Index(name = "idx_partida_mandante",  columnList = "clube_mandante_id"),
                @Index(name = "idx_partida_visitante", columnList = "clube_visitante_id"),
                @Index(name = "idx_partida_estadio",   columnList = "estadio_id")
        }
)
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // já existente
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clube_mandante_id", nullable = false)
    private Clube clubeMandante;

    // já existente
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clube_visitante_id", nullable = false)
    private Clube clubeVisitante;

    // novo relacionamento para estadio
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "estadio_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "partida_ibfk_3",
                    foreignKeyDefinition =
                            "FOREIGN KEY (estadio_id) " +
                                    "REFERENCES estadio(id) " +
                                    "ON UPDATE CASCADE " +
                                    "ON DELETE RESTRICT"
            )
    )
    private Estadio estadio;

    @Column(length = 10, nullable = false)
    private String resultado;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    // getters e setters…

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Clube getClubeMandante() { return clubeMandante; }
    public void setClubeMandante(Clube c) { this.clubeMandante = c; }

    public Clube getClubeVisitante() { return clubeVisitante; }
    public void setClubeVisitante(Clube c) { this.clubeVisitante = c; }

    public Estadio getEstadio() { return estadio; }
    public void setEstadio(Estadio e) { this.estadio = e; }

    public String getResultado() { return resultado; }
    public void setResultado(String r) { this.resultado = r; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dt) { this.dataHora = dt; }
}