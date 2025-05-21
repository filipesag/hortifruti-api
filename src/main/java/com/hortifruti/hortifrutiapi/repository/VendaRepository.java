package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.dto.venda.VendasPorSedeDTO;
import com.hortifruti.hortifrutiapi.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID> {

    @Query("""
        SELECT v.id as id, 
               v.dataVenda as dataVenda, 
               v.total as total, 
               v.statusVenda as statusVenda, 
               v.sede.nome as nome
        FROM Venda v
        WHERE v.sede.nome = :sede
        """)
    List<VendasPorSedeDTO> buscaVendasPorSede(@Param("sede") String sede);
}

