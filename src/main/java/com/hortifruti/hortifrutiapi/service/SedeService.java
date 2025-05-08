package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.sede.SedeRequestDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.sede.SedeMapper;
import com.hortifruti.hortifrutiapi.model.Sede;
import com.hortifruti.hortifrutiapi.repository.SedeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SedeService {

    @Autowired
    private final SedeRepository sedeRepository;
    @Autowired
    private final SedeMapper sedeMapper;

    public List<Sede> buscarTodos() {
        List<Sede> sedes = sedeRepository.findAll();
        return sedes;
    }

    @Transactional
    public SedeResponseDTO criarSede(SedeRequestDTO sede){
        Sede novaSede = sedeMapper.toEntity(sede);
        sedeRepository.save(novaSede);
        return sedeMapper.toDTO(novaSede);
    }
}
