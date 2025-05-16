package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, UUID> {
}

