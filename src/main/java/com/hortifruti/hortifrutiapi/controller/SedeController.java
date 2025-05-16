package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.dto.sede.SedeRequestDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.service.SedeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/sedes")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<SedeResponseDTO>> buscarTodos() {
        List<SedeResponseDTO> sedes = sedeService.buscarTodos();
        return ResponseEntity.ok().body(sedes);
    }

    @PostMapping
    public ResponseEntity<SedeResponseDTO> inserirNovaSede(@RequestBody @Valid SedeRequestDTO dto) {
        SedeResponseDTO novaSede = sedeService.criarSede(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaSede.id())
                .toUri();
        return ResponseEntity.created(uri).body(novaSede);
    }


}