package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteResponseDTO;
import com.hortifruti.hortifrutiapi.dto.formatoVenda.FormatoVendaDTO;
import com.hortifruti.hortifrutiapi.dto.venda.ItemVendaDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.balancete.BalanceteOperacaoMapper;
import com.hortifruti.hortifrutiapi.mappers.formatoVenda.FormatoVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.itemVenda.ItemVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.venda.VendaMapper;
import com.hortifruti.hortifrutiapi.model.*;
import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;
import com.hortifruti.hortifrutiapi.model.enums.TipoFormatoVenda;
import com.hortifruti.hortifrutiapi.repository.*;
import com.hortifruti.hortifrutiapi.service.exceptions.EstoqueInsuficienteException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private FormatoVendaRepository formatoVendaRepository;

    @Autowired
    private BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private EstoqueProdutoRepository estoqueProdutoRepository;

    private final ItemVendaMapper itemVendaMapper;

    private final VendaMapper vendaMapper;


    public VendaResponseDTO abreVenda(VendaRequestDTO dto, BalanceteOperacaoDTO dtoBalancete) {
        FormatoVenda formato = formatoVendaRepository.findById(dto.formatoVendaId())
                .orElseThrow(() -> new EntityNotFoundException("Formato de venda não encontrado"));

        Sede sede = null;
        if (dto.sedeId() != null) {
            sede = sedeRepository.findById(dto.sedeId())
                    .orElseThrow(() -> new EntityNotFoundException("Sede não encontrada"));
        }

        Venda venda = new Venda();
        venda.setDataVenda(dto.dataVenda() != null ? dto.dataVenda() : Instant.now());
        venda.setStatusVenda(StatusVenda.ABERTA);
        venda.setTotal(dto.total() != null ? dto.total() : BigDecimal.ZERO);
        venda.setFormatoVenda(formato);
        venda.setSede(sede);
        venda = vendaRepository.save(venda);

        BalanceteOperacaoVenda balancete = new BalanceteOperacaoVenda();
        balancete.setDataReceita(dtoBalancete.dataReceita() != null ? dtoBalancete.dataReceita() : Instant.now());
        balancete.setValorReceita(dtoBalancete.valorReceita() != null ? dtoBalancete.valorReceita() : BigDecimal.ZERO);
        balancete.setFormaPagamento(formaPagamentoRepository.findById(dtoBalancete.formaPagamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Forma de pagamento não encontrada")));
        balancete.setVenda(venda);
        balancete = balanceteOperacaoVendaRepository.save(balancete);

        venda.setBalanceteOperacaoVenda(balancete);
        venda = vendaRepository.save(venda);

        return vendaMapper.toDTO(venda);
    }

    @Transactional
    public VendaResponseDTO adicionarItensAVenda(UUID vendaId, List<ItemVendaDTO> itensDTO) {
        // 1. Buscar a venda (deve estar com status ABERTA)
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));

        if (venda.getStatusVenda() != StatusVenda.ABERTA) {
            throw new IllegalStateException("Não é possível adicionar produtos a uma venda fechada");
        }

        // 2. Verificar se a venda tem uma sede associada (necessária para controle de estoque)
        if (venda.getSede() == null) {
            throw new IllegalStateException("A venda deve estar associada a uma sede para adicionar produtos");
        }

        BigDecimal totalVenda = venda.getTotal() != null ? venda.getTotal() : BigDecimal.ZERO;

        for (ItemVendaDTO itemDTO : itensDTO) {
            // 3. Para cada item, buscar o produto e verificar estoque
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Produto não encontrado: " + itemDTO.produtoId()));

            // 4. Verificar estoque na sede específica
            EstoqueProduto estoque = estoqueProdutoRepository
                    .findByProdutoAndSede(produto, venda.getSede())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Produto não disponível na sede"));

            if (estoque.getQuantidade() < itemDTO.quantidade()) {
                throw new EstoqueInsuficienteException();
            }

            // 5. Criar o item de venda
            ItemVenda itemVenda = itemVendaMapper.toEntity(itemDTO);
            itemVenda.setVenda(venda);
            itemVenda.setProduto(produto);

            // Se preço unitário não foi informado, usar o preço do produto
            if (itemVenda.getPrecoUnit() == null) {
                itemVenda.setPrecoUnit(produto.getPreco().doubleValue());
            }

            // 6. Atualizar estoque
            estoque.setQuantidade(estoque.getQuantidade() - itemDTO.quantidade());
            estoqueProdutoRepository.save(estoque);

            // 7. Salvar item de venda
            itemVenda = itemVendaRepository.save(itemVenda);

            // Adicionar à lista de itens da venda
            venda.getItens().add(itemVenda);

            // 8. Atualizar total da venda
            totalVenda = totalVenda.add(
                    BigDecimal.valueOf(itemVenda.getPrecoUnit())
                            .multiply(BigDecimal.valueOf(itemVenda.getQuantidade()))
            );
        }

        // 9. Atualizar venda com novo total
        venda.setTotal(totalVenda);
        venda = vendaRepository.save(venda);

        // 10. Retornar a venda atualizada
        return vendaMapper.toDTO(venda);
    }


}
