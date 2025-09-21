package br.com.meli.apifutebol.repository;

import br.com.meli.apifutebol.dto.ClubeDto;
import br.com.meli.apifutebol.model.Clube;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubeRepository extends JpaRepository<Clube, Long> {
    //Utilizando padrao Query com JPQL
    @Query("""
    SELECT c
      FROM Clube c
     WHERE (:nome   IS NULL OR LOWER(c.nomeClube) LIKE LOWER(CONCAT('%', :nome, '%')))
       AND (:estado IS NULL OR c.estadoSede = :estado)
       AND (:ativo  IS NULL OR c.status = :ativo)
    """)
    Page<Clube> findByFiltro(
            @Param("nome")   String nome,
            @Param("estado") String estado,
            @Param("ativo")  Boolean ativo,
            Pageable pageable
    );
}


