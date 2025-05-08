package com.hortfruit.hortfruitapi.controller;

import com.hortfruit.hortfruitapi.model.Sede;
import com.hortfruit.hortfruitapi.service.SedeService;
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
    public ResponseEntity<Sede> inserirNovaSede(@RequestBody Sede sede) {
        Sede novaSede = sedeService.criaSede(sede);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaSede.getId())
                .toUri();
        return ResponseEntity.created(uri).body(novaSede);
    }


}