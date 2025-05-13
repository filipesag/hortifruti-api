package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.model.EstoqueProduto;
import com.hortifruti.hortifrutiapi.model.Produto;
import com.hortifruti.hortifrutiapi.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, UUID> {

    Optional<EstoqueProduto> findByProdutoAndSede(Produto produto, Sede sede);
}
