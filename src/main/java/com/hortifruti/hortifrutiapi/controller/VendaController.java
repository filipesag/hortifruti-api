package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.dto.venda.*;
import com.hortifruti.hortifrutiapi.service.VendaService;
import com.hortifruti.hortifrutiapi.service.exceptions.EstoqueInsuficienteException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/venda")
@RequiredArgsConstructor
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping("/buscar-todas-vendas")
    public ResponseEntity<List<VendaResponseDTO>> buscarTodasVendas() {
        List<VendaResponseDTO> vendas = vendaService.buscaVendas();
        return ResponseEntity.ok().body(vendas);
    }

    @GetMapping("/buscar-todas-vendas-por-sede")
    public List<VendasPorSedeDTO> buscarTodasVendas(@RequestParam String nomeSede) {
        List<VendasPorSedeDTO> vendas = vendaService.buscaVendasPorSede(nomeSede);
        return vendas;
    }

    @PostMapping("/aberta")
    public ResponseEntity<VendaResponseDTO> abreVenda(@RequestBody VendaComBalanceteRequestDTO dtoWrapper) {
        try {
            VendaResponseDTO response = vendaService.abreVenda(dtoWrapper.venda(), dtoWrapper.balancete());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{vendaId}/itens")
    public ResponseEntity<List<ItemVendaDTO>> adicionarItensVenda(
            @PathVariable UUID vendaId,
            @RequestBody List<ItemVendaAdicionadoDTO> itensDTO) {
        try {
            List<ItemVendaDTO> itensAdicionados = vendaService.adicionarItensAVenda(vendaId, itensDTO).itens();
            return ResponseEntity.ok(itensAdicionados);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EstoqueInsuficienteException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/fecha-venda/{vendaId}")
    public VendaResponseDTO aprovaVenda(@PathVariable UUID vendaId){
        VendaResponseDTO venda = vendaService.aprovaVenda(vendaId);
        return ResponseEntity.ok(venda).getBody();
    }

    @PostMapping("/cancela-venda/{vendaId}")
    public VendaResponseDTO cancelaVenda(@PathVariable UUID vendaId){
        VendaResponseDTO venda = vendaService.cancelaVenda(vendaId);
        return ResponseEntity.ok(venda).getBody();
    }

}
