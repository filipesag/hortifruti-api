//package com.hortifruti.hortifrutiapi.mappers.estoque;
//
//import com.hortifruti.hortifrutiapi.dto.estoque.EstoqueProdutoRequestDTO;
//import com.hortifruti.hortifrutiapi.dto.estoque.EstoqueProdutoResponseDTO;
//import com.hortifruti.hortifrutiapi.mappers.produto.ProdutoMapper;
//import com.hortifruti.hortifrutiapi.mappers.sede.SedeMapper;
//import com.hortifruti.hortifrutiapi.model.EstoqueProduto;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//
//@Mapper(componentModel = "spring",uses = {ProdutoMapper.class,SedeMapper.class})
//public interface EstoqueProdutoMapper {
//
//    @Mapping(target = "produto", source = "produtoId")
//    @Mapping(target = "sede", source = "sedeId")
//    EstoqueProduto toEntity(EstoqueProdutoRequestDTO dto);
//
//    EstoqueProdutoResponseDTO toDTO(EstoqueProduto estoqueProduto);
//}
