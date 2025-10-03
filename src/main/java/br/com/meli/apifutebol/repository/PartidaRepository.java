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
}
