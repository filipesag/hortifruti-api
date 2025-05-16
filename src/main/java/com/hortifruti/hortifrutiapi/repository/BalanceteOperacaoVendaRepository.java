package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.model.BalanceteOperacaoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BalanceteOperacaoVendaRepository extends JpaRepository<BalanceteOperacaoVenda, UUID> {
}
