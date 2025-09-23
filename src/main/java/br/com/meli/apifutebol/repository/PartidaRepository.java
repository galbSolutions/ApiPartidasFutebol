package br.com.meli.apifutebol.repository;

import br.com.meli.apifutebol.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository  extends JpaRepository<Partida, Long> {
}
