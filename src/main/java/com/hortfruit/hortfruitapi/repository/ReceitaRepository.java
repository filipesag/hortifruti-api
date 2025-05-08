package com.hortfruit.hortfruitapi.repository;

import com.hortfruit.hortfruitapi.model.FormaPagamento;
import com.hortfruit.hortfruitapi.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ReceitaRepository extends JpaRepository<Receita, UUID> {
}
