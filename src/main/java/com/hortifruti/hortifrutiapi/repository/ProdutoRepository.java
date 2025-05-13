package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.dto.produto.ProdutoEstoqueDTO;
import com.hortifruti.hortifrutiapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("""
    SELECT 
        p.nome AS nomeProduto, 
        p.unidadeMedida AS unidadeMedida, 
        e.quantidade AS quantidade, 
        s.nome AS nomeSede
    FROM Produto p
    JOIN EstoqueProduto e ON p.id = e.produto.id
    JOIN Sede s ON s.id = e.sede.id
    WHERE p.nome = :produto
    """)
    List<ProdutoEstoqueDTO> buscaSedeComEstoqueDeProduto(@Param("produto") String produto);

}
