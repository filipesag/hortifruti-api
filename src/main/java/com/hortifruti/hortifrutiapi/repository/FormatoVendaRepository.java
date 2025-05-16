package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.model.FormatoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FormatoVendaRepository extends JpaRepository<FormatoVenda, UUID> {
}

