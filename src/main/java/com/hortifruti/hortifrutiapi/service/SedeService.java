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

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SedeService {


    private final SedeRepository sedeRepository;

    private final SedeMapper sedeMapper;

    @Transactional
    public List<SedeResponseDTO> buscarTodos() {
        List<Sede> sedes = sedeRepository.findAll();
        List<SedeResponseDTO> listaSedes = new ArrayList<>();
        for (Sede sede : sedes) {
            listaSedes.add(sedeMapper.toDTO(sede));
        }
        return listaSedes;
    }

    @Transactional
    public SedeResponseDTO criarSede(SedeRequestDTO dto){
        Sede novaSede = sedeMapper.toEntity(dto);
        sedeRepository.save(novaSede);
        return sedeMapper.toDTO(novaSede);
    }
}
