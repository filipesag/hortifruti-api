package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.venda.ItemVendaAdicionadoDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.itemVenda.ItemVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.venda.VendaMapper;
import com.hortifruti.hortifrutiapi.model.*;
import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;
import com.hortifruti.hortifrutiapi.repository.*;
import com.hortifruti.hortifrutiapi.service.exceptions.EstoqueInsuficienteException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;

    private final FormatoVendaRepository formatoVendaRepository;

    private final BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    private final SedeRepository sedeRepository;
  
    private final ProdutoRepository produtoRepository;

    private final ItemVendaRepository itemVendaRepository;

    private final EstoqueProdutoRepository estoqueProdutoRepository;

    private final BalanceteOperacaoVendaService balanceteOperacaoVendaService;

    private final ItemVendaMapper itemVendaMapper;

    private final VendaMapper vendaMapper;

    @Transactional
    public VendaResponseDTO abreVenda(VendaRequestDTO dto, BalanceteOperacaoDTO dtoBalancete) {
        FormatoVenda formato = formatoVendaRepository.findById(dto.formatoVendaId())
                .orElseThrow(() -> new EntityNotFoundException("Formato de venda não encontrado"));

        Sede sede = null;
        if (dto.sedeId() != null) {
            sede = sedeRepository.findById(dto.sedeId())
                    .orElseThrow(() -> new EntityNotFoundException("Sede não encontrada"));
        }

        Venda venda = new Venda();
        venda.setDataVenda(Instant.now());
        venda.setStatusVenda(StatusVenda.ABERTA);
        venda.setTotal(dto.total() != null ? dto.total() : BigDecimal.ZERO);
        venda.setTipo_venda(formato);
        venda.setSede(sede);
        venda = vendaRepository.save(venda);

        BalanceteOperacaoVenda balancete = balanceteOperacaoVendaService.criaBalancete(dtoBalancete);
        balancete.setVenda(venda);
        balanceteOperacaoVendaRepository.save(balancete);

        venda.setBalanceteOperacaoVenda(balancete);
        venda = vendaRepository.save(venda);
        return vendaMapper.toDTO(venda);
    }

    @Transactional
    public VendaResponseDTO adicionarItensAVenda(UUID vendaId, List<ItemVendaAdicionadoDTO> itensDTO) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));

        if (venda.getStatusVenda() != StatusVenda.ABERTA) {
            throw new IllegalStateException("Não é possível adicionar produtos a uma venda fechada");
        }

        if (venda.getSede() == null) {
            throw new IllegalStateException("A venda deve estar associada a uma sede para adicionar produtos");
        }

        BigDecimal totalVenda = venda.getTotal() != null ? venda.getTotal() : BigDecimal.ZERO;

        for (ItemVendaAdicionadoDTO itemDTO : itensDTO) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Produto não encontrado: " + itemDTO.produtoId()));

            EstoqueProduto estoque = estoqueProdutoRepository
                    .findByProdutoAndSede(produto, venda.getSede())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Produto não disponível na sede"));

            if (estoque.getQuantidade() < itemDTO.quantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setVenda(venda);
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemDTO.quantidade());
            itemVenda.setPrecoUnit(produto.getPreco());

            estoque.setQuantidade(estoque.getQuantidade() - itemDTO.quantidade());
            estoqueProdutoRepository.save(estoque);

            itemVenda = itemVendaRepository.save(itemVenda);

            if (venda.getItens() == null) {
                venda.setItens(new ArrayList<>());
            }
            venda.getItens().add(itemVenda);

            totalVenda = totalVenda.add(
                    produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade())));
        }

        venda.setTotal(totalVenda);
        venda = vendaRepository.save(venda);
        return vendaMapper.toDTO(venda);
    }


    @Transactional
    public VendaResponseDTO aprovaVenda(UUID vendaId) {
        Venda venda = vendaRepository.findById(vendaId).orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));
        venda.setStatusVenda(StatusVenda.APROVADA);
        UUID balanceteId = venda.getBalanceteOperacaoVenda().getId();
        balanceteOperacaoVendaService.fechaBalancete(balanceteId,vendaId);
        return vendaMapper.toDTO(venda);
    }

    @Transactional
    public VendaResponseDTO cancelaVenda(UUID vendaId) {
        Venda venda = vendaRepository.findById(vendaId).orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));
        venda.setStatusVenda(StatusVenda.CANCELADA);
        List<ItemVenda> itens = venda.getItens();

        for(ItemVenda item : itens){
            List<EstoqueProduto> estoquesASerAdd = item.getProduto().getEstoques();
            for (EstoqueProduto estoque : estoquesASerAdd) {
                estoque.setQuantidade(estoque.getQuantidade() + item.getQuantidade());
                estoqueProdutoRepository.save(estoque);
            }
        }

        UUID balanceteId = venda.getBalanceteOperacaoVenda().getId();
        balanceteOperacaoVendaService.zeraBalancete(balanceteId,vendaId);
        return vendaMapper.toDTO(venda);
    }
}
