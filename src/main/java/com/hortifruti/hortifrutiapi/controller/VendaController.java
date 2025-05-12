package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.service.SedeService;
import com.hortifruti.hortifrutiapi.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/venda")
@RequiredArgsConstructor
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping("/aberta")
    public ResponseEntity<VendaResponseDTO> abreVenda(@RequestBody VendaRequestDTO dto) {
        try {
            VendaResponseDTO vendaResponseDTO = vendaService.abreVenda(dto);
            return new ResponseEntity<>(vendaResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
