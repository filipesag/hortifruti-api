package com.hortifruti.hortifrutiapi.mappers.estoque;


import com.hortifruti.hortifrutiapi.dto.estoque.EstoqueProdutoRequestDTO;
import com.hortifruti.hortifrutiapi.dto.estoque.EstoqueProdutoResponseDTO;
import com.hortifruti.hortifrutiapi.model.EstoqueProduto;
import com.hortifruti.hortifrutiapi.model.Produto;
import com.hortifruti.hortifrutiapi.model.Sede;
import com.hortifruti.hortifrutiapi.repository.ProdutoRepository;
import com.hortifruti.hortifrutiapi.repository.SedeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class EstoqueProdutoMapperAbs {

    @Autowired
    protected ProdutoRepository produtoRepository;

    @Autowired
    protected SedeRepository sedeRepository;

    public EstoqueProduto toEntity(EstoqueProdutoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        EstoqueProduto estoque = new EstoqueProduto();

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        Sede sede = sedeRepository.findById(dto.sedeId())
                .orElseThrow(() -> new EntityNotFoundException("Sede não encontrada"));

        estoque.setProduto(produto);
        estoque.setSede(sede);
        estoque.setQuantidade(dto.quantidade());

        return estoque;
    }

    public EstoqueProdutoResponseDTO toDTO(EstoqueProduto estoque) {
        if (estoque == null) {
            return null;
        }
        return new EstoqueProdutoResponseDTO(
                estoque.getId(),
                estoque.getProduto().getId(),
                estoque.getSede().getId(),
                estoque.getSede().getNome(),
                estoque.getQuantidade()
        );
    }
}

