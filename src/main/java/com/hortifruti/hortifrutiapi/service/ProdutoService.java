package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.estoque.EstoqueProdutoRequestDTO;
import com.hortifruti.hortifrutiapi.dto.estoque.EstoqueProdutoResponseDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutoRequestDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutoResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.estoque.EstoqueProdutoMapperAbs;
import com.hortifruti.hortifrutiapi.mappers.produto.ProdutoMapper;
import com.hortifruti.hortifrutiapi.model.EstoqueProduto;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.model.Produto;
import com.hortifruti.hortifrutiapi.model.Sede;
import com.hortifruti.hortifrutiapi.repository.EstoqueProdutoRepository;
import com.hortifruti.hortifrutiapi.repository.FornecedorRepository;
import com.hortifruti.hortifrutiapi.repository.ProdutoRepository;
import com.hortifruti.hortifrutiapi.repository.SedeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private EstoqueProdutoRepository estoqueProdutoRepository;

    private final ProdutoMapper produtoMapper;

    private final EstoqueProdutoMapperAbs estoqueProdutoMapper;

    public List<Produto> buscarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos;
    }

    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(dto.fornecedorId())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));

        Produto produto = produtoMapper.toEntity(dto);
        produto.setFornecedor(fornecedor);
        produtoRepository.save(produto);
        return produtoMapper.toDTO(produto);
    }

    @Transactional
    public EstoqueProdutoResponseDTO adicionarEstoque(EstoqueProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        Sede sede = sedeRepository.findById(dto.sedeId())
                .orElseThrow(() -> new EntityNotFoundException("Sede não encontrada"));
        EstoqueProduto estoque = new EstoqueProduto();
        estoque.setProduto(produto);
        estoque.setSede(sede);
        estoque.setQuantidade(dto.quantidade());
        estoqueProdutoRepository.save(estoque);
        return estoqueProdutoMapper.toDTO(estoque);
    }




}
