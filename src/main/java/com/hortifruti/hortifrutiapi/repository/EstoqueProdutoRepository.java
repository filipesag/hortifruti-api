package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.model.EstoqueProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, UUID> {
}
