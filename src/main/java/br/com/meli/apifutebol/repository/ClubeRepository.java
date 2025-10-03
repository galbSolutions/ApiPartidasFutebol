package br.com.meli.apifutebol.repository;

import br.com.meli.apifutebol.dto.ClubeDto;
import br.com.meli.apifutebol.model.Clube;
import br.com.meli.apifutebol.utils.Enum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubeRepository extends JpaRepository<Clube, Long> {

    Optional<Clube> findByNomeClubeAndEstadoSede(String nome, Enum.UF estado);
}


