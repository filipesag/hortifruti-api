package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SedeRepository extends JpaRepository<Sede, UUID> {
}
