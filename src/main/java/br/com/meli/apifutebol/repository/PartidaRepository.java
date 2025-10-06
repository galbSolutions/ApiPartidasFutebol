package br.com.meli.apifutebol.repository;

import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.model.Estadio;
import br.com.meli.apifutebol.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PartidaRepository  extends JpaRepository<Partida, Long> {
    List<Partida> findByClubeMandante_IdOrClubeVisitante_Id(
            Long mandanteId,
            Long visitanteId
    );
    // True se o clube já tem outra partida em ±48h da data informada
    boolean existsByClubeMandanteAndDataHoraBetween(
            Clube clube, LocalDateTime inicio, LocalDateTime fim);

    boolean existsByClubeVisitanteAndDataHoraBetween(
            Clube clube, LocalDateTime inicio, LocalDateTime fim);

    // True se já houver jogo neste estádio no mesmo dia
    boolean existsByEstadioAndDataHoraBetween(
            Estadio estadio, LocalDateTime diaInicio, LocalDateTime diaFim);

    boolean existsByClubeMandanteAndDataHoraBetweenAndIdNot(
            Clube clube,
            LocalDateTime inicio,
            LocalDateTime fim,
            Long idPartidaIgnorada);

    boolean existsByClubeVisitanteAndDataHoraBetweenAndIdNot(
            Clube clube,
            LocalDateTime inicio,
            LocalDateTime fim,
            Long idPartidaIgnorada);

    boolean existsByEstadioAndDataHoraBetweenAndIdNot(
            Estadio estadio,
            LocalDateTime diaInicio,
            LocalDateTime diaFim,
            Long idPartidaIgnorada);

    List<Partida> findByClubeMandanteIdOrClubeVisitanteId(Long mandanteId,
                                                          Long visitanteId);

    /**
     * Encontra todas as partidas onde
     * (clubeMandante.id = :idA AND clubeVisitante.id = :idB)
     *    OR
     * (clubeVisitante.id = :idA AND clubeMandante.id = :idB)
     *
     * ATENÇÃO: a ordem dos parâmetros deve corresponder à ordem no nome do método:
     *   1º idA, 2º idB → para MandanteId AND VisitanteId
     *   3º idA, 4º idB → para VisitanteId AND MandanteId
     */
    List<Partida>
            findByClubeMandanteIdAndClubeVisitanteIdOrClubeVisitanteIdAndClubeMandanteId(
            Long mandanteId1, // corresponde a “ClubeMandanteId” no primeiro trecho
            Long visitanteId1, // corresponde a “ClubeVisitanteId” no primeiro trecho
            Long visitanteId2, // corresponde a “ClubeVisitanteId” no segundo trecho
            Long mandanteId2   // corresponde a “ClubeMandanteId” no segundo trecho
    );

}
