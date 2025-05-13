package com.hortifruti.hortifrutiapi.dto.venda;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;

public record VendaComBalanceteRequestDTO(
        VendaRequestDTO venda,
        BalanceteOperacaoDTO balancete
){}
