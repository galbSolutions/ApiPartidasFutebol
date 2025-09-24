package br.com.meli.apifutebol.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estadio")
public class Estadio implements Serializable {

   // private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // NÃO é preciso definir columnDefinition aqui, a menos que seja absolutamente
    // necessário usar UNSIGNED no MySQL — mas o Hibernate ignora columnDefinition
    // em muitos dialects.
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome",
            length = 100,
            nullable = false)
    private String nome;

    @Column(name = "cidade",
            length = 100,
            nullable = false)
    private String cidade;

    @Column(name = "capacidade",
            nullable = false)
    private Integer capacidade;

    // Se a coluna no banco realmente se chama "status_",
    // mantenha o name. O JPA vai mapear boolean → TINYINT(1) no MySQL.
    @Column(name = "status_",
            nullable = false)
    private boolean status = true;


    // ----- Getters e Setters -----

    public Long getId() {
        return id;
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