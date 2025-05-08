package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
}
