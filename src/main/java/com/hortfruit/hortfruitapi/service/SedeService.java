package com.hortfruit.hortfruitapi.service;

import com.hortfruit.hortfruitapi.model.Sede;
import com.hortfruit.hortfruitapi.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    public List<Sede> buscarTodos() {
        List<Sede> sedes = sedeRepository.findAll();
        return sedes;
    }

    public Sede criaSede(Sede sede){
        return sedeRepository.save(sede);
    }
}
