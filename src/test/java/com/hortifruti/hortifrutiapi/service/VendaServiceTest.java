package com.hortifruti.hortifrutiapi.service;


import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.formatoVenda.FormatoVendaDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.formatoVenda.FormatoVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.itemVenda.ItemVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.itemVenda.ItemVendaMapperImpl;
import com.hortifruti.hortifrutiapi.mappers.sede.SedeMapper;
import com.hortifruti.hortifrutiapi.mappers.sede.SedeMapperImpl;
import com.hortifruti.hortifrutiapi.mappers.venda.VendaMapper;
import com.hortifruti.hortifrutiapi.mappers.venda.VendaMapperImpl;
import com.hortifruti.hortifrutiapi.model.*;
import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;
import com.hortifruti.hortifrutiapi.model.enums.TipoFormatoVenda;
import com.hortifruti.hortifrutiapi.model.enums.TipoPagamento;
import com.hortifruti.hortifrutiapi.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
class VendaServiceTest {

    @Mock
    private FormatoVendaRepository formatoVendaRepository;

    @Mock
    private SedeRepository sedeRepository;

    @Mock
    private FormaPagamentoRepository formaPagamentoRepository;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    @Mock
    private SedeMapper sedeMapper;

    @Mock
    private FormatoVendaMapper formatoVendaMapper;

    @Mock
    private ItemVendaMapper itemVendaMapper;

    @Mock
    private VendaMapperImpl vendaMapper;

    @InjectMocks
    private VendaService vendaService;


    @Test
    @DisplayName("Deve abrir venda com sucesso")
    void abreVenda() {
        // Arrange
//        UUID formatoId = UUID.randomUUID();
//        UUID formaPagamentoId = UUID.randomUUID();
//        UUID sedeId = UUID.randomUUID();
//        UUID vendaId = UUID.randomUUID();
//
//        FormatoVenda formato = new FormatoVenda();
//        formato.setId(formatoId);
//        formato.setTipo(TipoFormatoVenda.FISICA);
//
//        Sede sede = new Sede();
//        sede.setId(sedeId);
//
//        FormaPagamento formaPagamento = new FormaPagamento();
//        formaPagamento.setId(formaPagamentoId);
//
//        Venda vendaSalva = new Venda();
//        vendaSalva.setId(vendaId);
//        vendaSalva.setDataVenda(Instant.now());
//        vendaSalva.setStatusVenda(StatusVenda.ABERTA);
//        vendaSalva.setTipo_venda(formato);
//        vendaSalva.setSede(sede);
//        vendaSalva.setTotal(BigDecimal.valueOf(100));
//
//        BalanceteOperacaoVenda balanceteSalvo = new BalanceteOperacaoVenda();
//        balanceteSalvo.setId(UUID.randomUUID());
//        balanceteSalvo.setValorReceita(BigDecimal.valueOf(100));
//        balanceteSalvo.setFormaPagamento(formaPagamento);
//        balanceteSalvo.setVenda(vendaSalva);
//
//        // Mocks dos repositórios
//        when(formatoVendaRepository.findById(formatoId)).thenReturn(Optional.of(formato));
//        when(sedeRepository.findById(sedeId)).thenReturn(Optional.of(sede));
//        when(formaPagamentoRepository.findById(formaPagamentoId)).thenReturn(Optional.of(formaPagamento));
//        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);
//        when(balanceteOperacaoVendaRepository.save(any(BalanceteOperacaoVenda.class))).thenReturn(balanceteSalvo);
//
//        // Mocks dos mappers
//        when(sedeMapper.toDTO(any())).thenReturn(new SedeResponseDTO(
//                sedeId, "Sede Guajajaras", "Rua Guajajaras", "Centro", "123", "Belo Horizonte", "MG", "SACOLÃO"
//        ));
//        when(formatoVendaMapper.toDTO(any())).thenReturn(new FormatoVendaDTO(formatoId, TipoFormatoVenda.FISICA));
//        when(itemVendaMapper.toDTOList(any())).thenReturn(List.of());
//
//        // DTOs de entrada
//        VendaRequestDTO vendaRequestDTO = new VendaRequestDTO(
//                Instant.now(),
//                BigDecimal.valueOf(100),
//                StatusVenda.ABERTA,
//                sedeId,
//                formatoId
//        );
//
//        BalanceteOperacaoDTO balanceteDTO = new BalanceteOperacaoDTO(
//                Instant.now(),
//                BigDecimal.valueOf(100),
//                formaPagamentoId
//        );
//
//        // Act
//        VendaResponseDTO responseDTO = vendaService.abreVenda(vendaRequestDTO, balanceteDTO);
//
//        // Assert
//        assertNotNull(responseDTO);
//        assertEquals(vendaId, responseDTO.id());
//        assertEquals(StatusVenda.ABERTA, responseDTO.statusVenda());
//        assertEquals(BigDecimal.valueOf(100), responseDTO.total());
//        assertEquals("FISICA", responseDTO.tipo_venda());
//        assertEquals(sedeId, responseDTO.sede().id());
//        assertTrue(responseDTO.itens().isEmpty());
    }


    @Test
    @DisplayName("Deve adicionar itens de compra a venda com sucesso")
    void adicionarItensAVenda() {
    }

    @Test
    @DisplayName("Deve aprovar venda com sucesso")
    void aprovaVenda() {
    }

    @Test
    @DisplayName("Deve cancelar a venda com sucesso")
    void cancelaVenda() {
    }
}