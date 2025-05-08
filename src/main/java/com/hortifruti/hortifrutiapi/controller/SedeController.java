package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.dto.sede.SedeRequestDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.model.Sede;
import com.hortifruti.hortifrutiapi.service.SedeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sedes")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Sede>> buscarTodos() {
        List<Sede> sedes = sedeService.buscarTodos();
        return ResponseEntity.ok().body(sedes);
    }

    @PostMapping
    public ResponseEntity<SedeResponseDTO> inserirNovaSede(@RequestBody @Valid SedeRequestDTO sede) {
        SedeResponseDTO novaSede = sedeService.criarSede(sede);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaSede.id())
                .toUri();
        return ResponseEntity.created(uri).body(novaSede);
    }


}