package br.com.meli.apifutebol.repository;

import br.com.meli.apifutebol.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository  extends JpaRepository<Partida, Long> {
    List<Partida> findByClubeMandante_IdOrClubeVisitante_Id(
            Long mandanteId,
            Long visitanteId
    );
}
