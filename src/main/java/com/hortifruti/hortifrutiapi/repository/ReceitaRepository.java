package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ReceitaRepository extends JpaRepository<Receita, UUID> {
}
