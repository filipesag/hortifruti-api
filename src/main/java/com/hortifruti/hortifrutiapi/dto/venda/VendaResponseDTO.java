package com.hortifruti.hortifrutiapi.dto.venda;


import com.hortifruti.hortifrutiapi.dto.formatoVenda.FormatoVendaDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record VendaResponseDTO(
        UUID id,
        Instant dataVenda,
        BigDecimal total,
        StatusVenda statusVenda,
        SedeResponseDTO sede,
        FormatoVendaDTO tipo_venda,
        List<ItemVendaDTO> itens
) {}
