package br.com.meli.apifutebol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import br.com.meli.apifutebol.utils.Enum;


public class ApiException {
    private ApiException() { /* evita instanciação */ }

    public static ResponseStatusException clubeNaoEncontrado(Long id) {
        return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Clube não encontrado: %d", id)
        );
    }

    public static ResponseStatusException dataCriacaoFutura() {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Data de criação não pode ser futura."
        );
    }

    public static ResponseStatusException dataCriacaoConflito() {
        return new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Data de criação não pode ser posterior a partidas existentes."
        );
    }

    public static ResponseStatusException nomeEstadoDuplicado(String nome, Enum.UF estado) {
        return new ResponseStatusException(
                HttpStatus.CONFLICT,
                String.format("Já existe outro clube com nome \"%s\" no estado \"%s\".", nome, estado)
        );
    }

    public static ResponseStatusException erroBancoDados(Long id, Throwable causa) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                String.format("Erro interno no banco de dados ao processar clube %d.", id),
                causa
        );
    }

    public static ResponseStatusException badRequestGenerico(String detalhe, Throwable causa) {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Requisição inválida: %s", detalhe),
                causa
        );
    }
    public static ResponseStatusException conflictGenerico(String detalhe) {
        return new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Conflito de dados: " + detalhe
        );
    }
    public static ResponseStatusException clubeInativo(Long id) {
        return new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Clube inativo: " + id
        );
    }
}
