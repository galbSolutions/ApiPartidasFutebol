package br.com.meli.apifutebol.dto;

import br.com.meli.apifutebol.utils.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ClubeUpdateDto {
    @NotBlank(message = "Nome não pode ficar em branco")
    @Size(min = 2, message = "Nome deve ter ao menos 2 caracteres")
    private String nomeClube;

    @NotNull(message = "EstadoSede é obrigatório")
    private Enum.UF uf;

    @NotNull(message = "Data de criação é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;

    public ClubeUpdateDto() {
    }

    public ClubeUpdateDto(String nomeClube, Enum.UF uf, LocalDate dataCriacao) {
        this.nomeClube    = nomeClube;
        this.uf   = uf;
        this.dataCriacao  = dataCriacao;
    }

    public String getNomeClube() {
        return nomeClube;
    }
    public void setNomeClube(String nomeClube) {
        this.nomeClube = nomeClube;
    }

    public Enum.UF getEstadoSede() {
        return uf;
    }
    public void setEstadoSede(Enum.UF uf) {
        this.uf = uf;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
