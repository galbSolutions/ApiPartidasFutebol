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
    //Utilizando padrao Query com JPQL
    @Query(
            "SELECT c " +
            "FROM   Clube c " +
            "WHERE  (:nome   IS NULL OR LOWER(c.nomeClube) LIKE LOWER(CONCAT('%',:nome,'%'))) " +
            "  AND  (:estado IS NULL OR c.estadoSede = :estado) " +
            "  AND  (:status IS NULL OR c.status     = :status)"
    )
    Page<Clube> findByFiltro(
            @Param("nome")   String nome,
            @Param("estado") Enum.UF estado,
            @Param("status") Boolean status,
            Pageable pageable
    );
    Optional<Clube> findByNomeClubeAndEstadoSede(String nome, Enum.UF estado);
}


