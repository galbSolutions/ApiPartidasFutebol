package br.com.meli.apifutebol.repository;

import br.com.meli.apifutebol.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadioRepository extends JpaRepository<Estadio, Long> {
}
