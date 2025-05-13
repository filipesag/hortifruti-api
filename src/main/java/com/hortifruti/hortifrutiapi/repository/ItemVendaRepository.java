package com.hortifruti.hortifrutiapi.repository;


import com.hortifruti.hortifrutiapi.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, UUID> {
}