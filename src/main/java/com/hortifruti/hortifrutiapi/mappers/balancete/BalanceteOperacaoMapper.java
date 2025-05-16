package com.hortifruti.hortifrutiapi.mappers.balancete;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteResponseDTO;
import com.hortifruti.hortifrutiapi.model.BalanceteOperacaoVenda;
import com.hortifruti.hortifrutiapi.model.FormaPagamento;
import com.hortifruti.hortifrutiapi.model.Venda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceteOperacaoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "formaPagamento", source = "formaPagamento")
    @Mapping(target = "venda", source = "venda")
    BalanceteOperacaoVenda toEntity(BalanceteOperacaoDTO dto, FormaPagamento formaPagamento, Venda venda);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "formaPagamento.id", target = "formaPagamentoId")
    @Mapping(source = "formaPagamento.descricao", target = "formaPagamentoDescricao")
    @Mapping(source = "venda.id", target = "vendaId")
    BalanceteResponseDTO toResponseDTO(BalanceteOperacaoVenda entity);
}