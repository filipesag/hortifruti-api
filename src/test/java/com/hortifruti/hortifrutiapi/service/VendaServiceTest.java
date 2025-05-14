package com.hortifruti.hortifrutiapi.service;


import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.formatoVenda.FormatoVendaDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.formatoVenda.FormatoVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.itemVenda.ItemVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.sede.SedeMapper;
import com.hortifruti.hortifrutiapi.mappers.venda.VendaMapper;
import com.hortifruti.hortifrutiapi.model.*;
import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;
import com.hortifruti.hortifrutiapi.model.enums.TipoFormatoVenda;
import com.hortifruti.hortifrutiapi.model.enums.TipoPagamento;
import com.hortifruti.hortifrutiapi.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private FormatoVendaRepository formatoVendaRepository;

    @Mock
    private BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    @Mock
    private SedeRepository sedeRepository;

    @Mock
    private FormaPagamentoRepository formaPagamentoRepository;

    @Mock
    private BalanceteOperacaoVendaService balanceteOperacaoVendaService;

    @Mock
    private VendaMapper vendaMapper;

    @InjectMocks
    private VendaService vendaService;

    @Test
    @DisplayName("Deve abrir venda com sucesso quando todos os dados são válidos")
    void abreVendaSucesso() {
        UUID formatoId = UUID.randomUUID();
        UUID formaPagamentoId = UUID.randomUUID();
        UUID sedeId = UUID.randomUUID();
        UUID vendaId = UUID.randomUUID();

        FormatoVenda formato = new FormatoVenda(formatoId, TipoFormatoVenda.FISICA,null);
        Sede sede = new Sede(sedeId, "Sede Teste", "Endereço", "Bairro", "123", "Cidade", "UF", "TIPO",null,null);
        FormaPagamento formaPagamento = new FormaPagamento(formaPagamentoId, TipoPagamento.CARTAO_CREDITO_GOLDMONEY, BigDecimal.valueOf(2.5),null);

        Venda vendaSalva = new Venda();
        vendaSalva.setId(vendaId);
        vendaSalva.setDataVenda(Instant.now());
        vendaSalva.setStatusVenda(StatusVenda.ABERTA);
        vendaSalva.setTipo_venda(formato);
        vendaSalva.setSede(sede);
        vendaSalva.setTotal(BigDecimal.valueOf(100));

        BalanceteOperacaoVenda balanceteSalvo = new BalanceteOperacaoVenda();
        balanceteSalvo.setId(UUID.randomUUID());
        balanceteSalvo.setValorReceita(BigDecimal.valueOf(100));
        balanceteSalvo.setFormaPagamento(formaPagamento);
        balanceteSalvo.setVenda(vendaSalva);

        VendaRequestDTO vendaRequestDTO = new VendaRequestDTO(
                Instant.now(),
                BigDecimal.valueOf(100),
                StatusVenda.ABERTA,
                sedeId,
                formatoId
        );

        BalanceteOperacaoDTO balanceteDTO = new BalanceteOperacaoDTO(
                Instant.now(),
                BigDecimal.valueOf(100),
                formaPagamentoId
        );

        VendaResponseDTO expectedResponse = new VendaResponseDTO(
                vendaId,
                Instant.now(),
                BigDecimal.valueOf(100),
                StatusVenda.ABERTA,
                new SedeResponseDTO(sedeId, "Sede Teste", "Endereço", "Bairro", "123", "Cidade", "UF", "TIPO"),
                new FormatoVendaDTO(formatoId, TipoFormatoVenda.FISICA),
                List.of()
        );

        when(formatoVendaRepository.findById(formatoId)).thenReturn(Optional.of(formato));
        when(sedeRepository.findById(sedeId)).thenReturn(Optional.of(sede));
        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);
        when(balanceteOperacaoVendaService.criaBalancete(any(BalanceteOperacaoDTO.class))).thenReturn(balanceteSalvo);
        when(balanceteOperacaoVendaRepository.save(any(BalanceteOperacaoVenda.class))).thenReturn(balanceteSalvo);
        when(vendaMapper.toDTO(any(Venda.class))).thenReturn(expectedResponse);

        VendaResponseDTO responseDTO = vendaService.abreVenda(vendaRequestDTO, balanceteDTO);

        assertNotNull(responseDTO);
        assertEquals(vendaId, responseDTO.id());
        assertEquals(StatusVenda.ABERTA, responseDTO.statusVenda());
        assertEquals(BigDecimal.valueOf(100), responseDTO.total());
        assertEquals(formatoId, responseDTO.tipo_venda().id());
        assertEquals(sedeId, responseDTO.sede().id());

        verify(formatoVendaRepository).findById(formatoId);
        verify(sedeRepository).findById(sedeId);
        verify(vendaRepository, times(2)).save(any(Venda.class));
        verify(balanceteOperacaoVendaService).criaBalancete(any(BalanceteOperacaoDTO.class));
        verify(balanceteOperacaoVendaRepository).save(any(BalanceteOperacaoVenda.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando formato de venda não for encontrado")
    void abreVendaComExceptionFormatoVendaNaoEncontrado() {
        UUID formatoId = UUID.randomUUID();
        UUID sedeId = UUID.randomUUID();

        VendaRequestDTO vendaRequestDTO = new VendaRequestDTO(
                Instant.now(),
                BigDecimal.valueOf(100),
                StatusVenda.ABERTA,
                sedeId,
                formatoId
        );

        BalanceteOperacaoDTO balanceteDTO = new BalanceteOperacaoDTO(
                Instant.now(),
                BigDecimal.valueOf(100),
                UUID.randomUUID()
        );
        FormatoVenda formato = new FormatoVenda(formatoId, TipoFormatoVenda.FISICA,null);
        when(formatoVendaRepository.findById(formatoId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> vendaService.abreVenda(vendaRequestDTO, balanceteDTO));
        verify(formatoVendaRepository).findById(formatoId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando sede não for encontrada")
    void abreVendaComExceptionSedeNaoEncontrada() {
        UUID formatoId = UUID.randomUUID();
        UUID sedeId = UUID.randomUUID();

        VendaRequestDTO vendaRequestDTO = new VendaRequestDTO(
                Instant.now(),
                BigDecimal.valueOf(100),
                StatusVenda.ABERTA,
                sedeId,
                formatoId
        );

        BalanceteOperacaoDTO balanceteDTO = new BalanceteOperacaoDTO(
                Instant.now(),
                BigDecimal.valueOf(100),
                UUID.randomUUID()
        );
        FormatoVenda formato = new FormatoVenda(formatoId, TipoFormatoVenda.FISICA,null);
        when(formatoVendaRepository.findById(formatoId)).thenReturn(Optional.of(formato));
        when(sedeRepository.findById(sedeId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> vendaService.abreVenda(vendaRequestDTO, balanceteDTO));
        verify(sedeRepository).findById(sedeId);
    }

    @Test
    @DisplayName("Deve abrir venda com valores padrão quando dados não forem fornecidos")
    void abreVendaComDadosNulosERetornaValorPadrao() {
        // Arrange
        UUID formatoId = UUID.randomUUID();
        UUID formaPagamentoId = UUID.randomUUID();
        UUID vendaId = UUID.randomUUID();
        UUID sedeId = UUID.randomUUID();

        FormatoVenda formato = new FormatoVenda(formatoId, TipoFormatoVenda.FISICA, null);

        Venda vendaSalva = new Venda();
        vendaSalva.setId(UUID.randomUUID());
        vendaSalva.setDataVenda(Instant.now());
        vendaSalva.setStatusVenda(StatusVenda.ABERTA);
        vendaSalva.setTipo_venda(formato);
        vendaSalva.setTotal(BigDecimal.ZERO);

        BalanceteOperacaoVenda balanceteSalvo = new BalanceteOperacaoVenda();
        balanceteSalvo.setId(UUID.randomUUID());
        balanceteSalvo.setValorReceita(BigDecimal.ZERO);
        balanceteSalvo.setFormaPagamento(new FormaPagamento(formaPagamentoId, TipoPagamento.CARTAO_CREDITO_GOLDMONEY, BigDecimal.ZERO, null));

        VendaRequestDTO vendaRequestDTO = new VendaRequestDTO(
                null,
                null,
                null,
                sedeId,
                formatoId
        );

        BalanceteOperacaoDTO balanceteDTO = new BalanceteOperacaoDTO(
                null,
                null,
                formaPagamentoId
        );

        VendaResponseDTO expectedResponse = new VendaResponseDTO(
                vendaId,
                Instant.now(),
                BigDecimal.valueOf(0),
                StatusVenda.ABERTA,
                new SedeResponseDTO(sedeId, "Sede Teste", "Endereço", "Bairro", "123", "Cidade", "UF", "TIPO"),
                new FormatoVendaDTO(formatoId, TipoFormatoVenda.FISICA),
                List.of()
        );

        Sede sede = new Sede(sedeId, "Sede Teste", "Endereço", "Bairro", "123", "Cidade", "UF", "TIPO", null, null);

        // Configuração dos mocks
        when(formatoVendaRepository.findById(formatoId)).thenReturn(Optional.of(formato));
        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);
        when(sedeRepository.findById(sedeId)).thenReturn(Optional.of(sede));
        when(balanceteOperacaoVendaService.criaBalancete(any(BalanceteOperacaoDTO.class))).thenReturn(balanceteSalvo);
        when(balanceteOperacaoVendaRepository.save(any(BalanceteOperacaoVenda.class))).thenReturn(balanceteSalvo);
        when(vendaMapper.toDTO(any(Venda.class))).thenReturn(expectedResponse);

        // Act
        VendaResponseDTO responseDTO = vendaService.abreVenda(vendaRequestDTO, balanceteDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(BigDecimal.ZERO, responseDTO.total());
        assertEquals(StatusVenda.ABERTA, responseDTO.statusVenda());
        assertNotNull(responseDTO.dataVenda());
        assertNotNull(responseDTO.sede());
        assertEquals(sedeId, responseDTO.sede().id());
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