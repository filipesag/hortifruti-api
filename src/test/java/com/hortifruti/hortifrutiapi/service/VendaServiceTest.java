package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.formatoVenda.FormatoVendaDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.dto.venda.ItemVendaAdicionadoDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.itemVenda.ItemVendaMapper;
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
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
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
    private EstoqueProdutoRepository estoqueProdutoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ItemVendaRepository itemVendaRepository;

    @Mock
    private FormaPagamentoRepository formaPagamentoRepository;

    @Mock
    private BalanceteOperacaoVendaService balanceteOperacaoVendaService;

    @Mock
    private VendaMapper vendaMapper;

    @Mock
    private ItemVendaMapper itemVendaMapper;

    @InjectMocks
    private VendaService vendaService;

    private UUID formatoId;
    private UUID formaPagamentoId;
    private UUID sedeId;
    private UUID vendaId;
    private UUID produtoId;
    private UUID estoqueId;
    private UUID fornecedorId;
    private UUID itemVendaId;

    @BeforeEach
    void setUp() {
        formatoId = UUID.randomUUID();
        formaPagamentoId = UUID.randomUUID();
        sedeId = UUID.randomUUID();
        vendaId = UUID.randomUUID();
        produtoId = UUID.randomUUID();
        estoqueId = UUID.randomUUID();
        fornecedorId = UUID.randomUUID();
        itemVendaId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve abrir venda com sucesso quando todos os dados são válidos")
    void abreVendaSucesso()  {
        FormatoVenda formato = criarFormato(formatoId);
        Sede sede = criarSede(sedeId);
        FormaPagamento formaPagamento = criarFormaPagamento(formaPagamentoId);
        Venda venda = criarVenda(vendaId, formato, sede,null);
        BalanceteOperacaoVenda balancete = criarBalancete(formaPagamento, venda);

        VendaRequestDTO requestDTO = criarVendaRequestDTO(sedeId, formatoId);
        BalanceteOperacaoDTO balanceteDTO = criarBalanceteDTO(formaPagamentoId);
        VendaResponseDTO expectedResponse = criarVendaResponseEsperado();

        mockRepositorios(formato, sede, venda, balancete);

        when(balanceteOperacaoVendaService.criaBalancete(any())).thenReturn(balancete);
        when(vendaMapper.toDTO(any())).thenReturn(expectedResponse);

        VendaResponseDTO response = vendaService.abreVenda(requestDTO, balanceteDTO);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertNotNull(response);
        assertEquals(vendaId, response.id());
        assertEquals(StatusVenda.ABERTA, response.statusVenda());
        assertEquals(BigDecimal.valueOf(258), response.total());
        assertEquals(formatoId, response.tipo_venda().id());
        assertEquals(sedeId, response.sede().id());

        verify(formatoVendaRepository).findById(formatoId);
        verify(sedeRepository).findById(sedeId);
        verify(vendaRepository, times(2)).save(any(Venda.class));
        verify(balanceteOperacaoVendaService).criaBalancete(any(BalanceteOperacaoDTO.class));
        verify(balanceteOperacaoVendaRepository).save(any(BalanceteOperacaoVenda.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando formato de venda não for encontrado")
    void abreVendaComExceptionFormatoVendaNaoEncontrado() {
        FormatoVenda formato = criarFormato(formatoId);
        Sede sede = criarSede(sedeId);

        VendaRequestDTO vendaRequestDTO = new VendaRequestDTO(
                Instant.now(),
                BigDecimal.valueOf(258),
                StatusVenda.ABERTA,
                sede.getId(),
                formato.getId()
        );

        BalanceteOperacaoDTO balanceteDTO = new BalanceteOperacaoDTO(
                Instant.now(),
                BigDecimal.valueOf(258),
                UUID.randomUUID()
        );
        when(formatoVendaRepository.findById(formatoId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> vendaService.abreVenda(vendaRequestDTO, balanceteDTO));
        verify(formatoVendaRepository).findById(formatoId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando sede não for encontrada")
    void abreVendaComExceptionSedeNaoEncontrada() {
        FormatoVenda formato = criarFormato(formatoId);
        Sede sede = criarSede(sedeId);
        VendaRequestDTO vendaRequestDTO = new VendaRequestDTO(
                Instant.now(),
                BigDecimal.valueOf(258),
                StatusVenda.ABERTA,
                sede.getId(),
                formato.getId()
        );
        BalanceteOperacaoDTO balanceteDTO = new BalanceteOperacaoDTO(
                Instant.now(),
                BigDecimal.valueOf(258),
                UUID.randomUUID()
        );

        when(formatoVendaRepository.findById(formatoId)).thenReturn(Optional.of(formato));
        when(sedeRepository.findById(sedeId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> vendaService.abreVenda(vendaRequestDTO, balanceteDTO));
        verify(sedeRepository).findById(sedeId);
    }

    @Test
    @DisplayName("Deve abrir venda com valores padrão quando dados não forem fornecidos")
    void abreVendaComDadosNulosERetornaValorPadrao() {
        FormatoVenda formato = criarFormato(formatoId);
        Sede sede = criarSede(sedeId);
        FormaPagamento formaPagamento = criarFormaPagamento(formaPagamentoId);
        Venda venda = criarVenda(vendaId, formato, sede,null);
        BalanceteOperacaoVenda balancete = criarBalancete(formaPagamento, venda);
        venda.setBalanceteOperacaoVenda(balancete);

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

        when(formatoVendaRepository.findById(formatoId)).thenReturn(Optional.of(formato));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);
        when(sedeRepository.findById(sedeId)).thenReturn(Optional.of(sede));
        when(balanceteOperacaoVendaService.criaBalancete(any(BalanceteOperacaoDTO.class))).thenReturn(balancete);
        when(balanceteOperacaoVendaRepository.save(any(BalanceteOperacaoVenda.class))).thenReturn(balancete);
        when(vendaMapper.toDTO(any(Venda.class))).thenReturn(expectedResponse);

        VendaResponseDTO responseDTO = vendaService.abreVenda(vendaRequestDTO, balanceteDTO);

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
        FormatoVenda formato = criarFormato(formatoId);
        Sede sede = criarSede(sedeId);
        Fornecedor fornecedor = criaForncedor(fornecedorId, null);
        Produto produto = criaProduto(produtoId, fornecedor, null, null);
        EstoqueProduto estoqueProduto = criaEstoqueProduto(estoqueId, produto, sede);
        ItemVendaAdicionadoDTO itemVendaAdicionadoDTO = criaItemVendaAddDTO(produtoId);
        Venda venda = criarVenda(vendaId, formato, sede, null);

        venda.setTotal(BigDecimal.ZERO);

        List<ItemVendaAdicionadoDTO> itemVendaAdicionado = List.of(itemVendaAdicionadoDTO);
        ItemVenda itemVenda = criaItemVenda(itemVendaId, venda, produto);

        when(vendaRepository.findById(vendaId)).thenReturn(Optional.of(venda));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        when(estoqueProdutoRepository.findByProdutoAndSede(produto, venda.getSede())).thenReturn(Optional.of(estoqueProduto));
        when(estoqueProdutoRepository.save(any(EstoqueProduto.class))).thenReturn(estoqueProduto);
        when(itemVendaRepository.save(any(ItemVenda.class))).thenReturn(itemVenda);
        when(itemVendaMapper.toEntity(itemVendaAdicionadoDTO)).thenReturn(itemVenda);

        VendaResponseDTO expectedResponse = criarVendaResponseEsperado();
        when(vendaMapper.toDTO(venda)).thenReturn(expectedResponse);

        VendaResponseDTO response = vendaService.adicionarItensAVenda(vendaId, itemVendaAdicionado);

        assertNotNull(response);
        assertEquals(expectedResponse.dataVenda(), response.dataVenda());
        assertEquals(expectedResponse.total(), response.total());
        assertEquals(expectedResponse.statusVenda(), response.statusVenda());
        assertEquals(expectedResponse.tipo_venda().id(), response.tipo_venda().id());
        assertEquals(expectedResponse.sede().id(), response.sede().id());
    }

    @Test
    @DisplayName("Deve aprovar venda com sucesso")
    void aprovaVenda() {
        FormatoVenda formato = criarFormato(formatoId);
        Sede sede = criarSede(sedeId);
        FormaPagamento formaPagamento = criarFormaPagamento(formaPagamentoId);
        Venda venda = criarVenda(vendaId, formato, sede,null);
        BalanceteOperacaoVenda balancete = criarBalancete(formaPagamento, venda);
        VendaResponseDTO expectedResponse = vendaAprovadaResponseEsperado();
        venda.setBalanceteOperacaoVenda(balancete);

        when(vendaRepository.findById(vendaId)).thenReturn(Optional.of(venda));
        when(vendaMapper.toDTO(venda)).thenReturn(expectedResponse);

        VendaResponseDTO response = vendaService.aprovaVenda(vendaId);

        assertNotNull(response);
        assertEquals(expectedResponse.dataVenda(), response.dataVenda());
        assertEquals(expectedResponse.total(), response.total());
        assertEquals(expectedResponse.statusVenda(), response.statusVenda());
        assertEquals(expectedResponse.tipo_venda().id(), response.tipo_venda().id());
        assertEquals(expectedResponse.sede().id(), response.sede().id());
    }

    @Test
    @DisplayName("Deve cancelar a venda com sucesso")
    void cancelaVenda() {
        FormatoVenda formato = criarFormato(formatoId);
        Sede sede = criarSede(sedeId);
        FormaPagamento formaPagamento = criarFormaPagamento(formaPagamentoId);
        Venda venda = criarVenda(vendaId, formato, sede,null);
        BalanceteOperacaoVenda balancete = criarBalancete(formaPagamento, venda);
        VendaResponseDTO expectedResponse = vendaCanceladaResponseEsperado();
        venda.setBalanceteOperacaoVenda(balancete);

        when(vendaRepository.findById(vendaId)).thenReturn(Optional.of(venda));
        when(vendaMapper.toDTO(venda)).thenReturn(expectedResponse);

        VendaResponseDTO response = vendaService.cancelaVenda(vendaId);

        assertNotNull(response);
        assertEquals(expectedResponse.dataVenda(), response.dataVenda());
        assertEquals(expectedResponse.total(), response.total());
        assertEquals(expectedResponse.statusVenda(), response.statusVenda());
        assertEquals(expectedResponse.tipo_venda().id(), response.tipo_venda().id());
        assertEquals(expectedResponse.sede().id(), response.sede().id());
    }

    private FormatoVenda criarFormato(UUID id) {
        return new FormatoVenda(id, TipoFormatoVenda.FISICA, null);
    }

    private Sede criarSede(UUID id) {
        return new Sede(id, "Sede Teste", "Rua A", "Bairro A", "123", "Cidade X", "UF", "PRINCIPAL", null, null);
    }

    private FormaPagamento criarFormaPagamento(UUID id) {
        return new FormaPagamento(id, TipoPagamento.CARTAO_CREDITO_GOLDMONEY, BigDecimal.valueOf(2.5), null);
    }

    private Venda criarVenda(UUID id, FormatoVenda formato, Sede sede, BalanceteOperacaoVenda balancete) {
        return new Venda(id, Instant.now(), BigDecimal.valueOf(100), StatusVenda.ABERTA, balancete,sede,null, new ArrayList<>());
    }

    private BalanceteOperacaoVenda criarBalancete(FormaPagamento formaPagamento, Venda venda) {
        return new BalanceteOperacaoVenda(UUID.randomUUID(), Instant.now(), BigDecimal.valueOf(100), formaPagamento, venda);
    }

    private VendaRequestDTO criarVendaRequestDTO(UUID sedeId, UUID formatoId) {
        return new VendaRequestDTO(Instant.now(), BigDecimal.valueOf(100), StatusVenda.ABERTA, sedeId, formatoId);
    }

    private BalanceteOperacaoDTO criarBalanceteDTO(UUID formaPagamentoId) {
        return new BalanceteOperacaoDTO(Instant.now(), BigDecimal.valueOf(258), formaPagamentoId);
    }

    private ItemVendaAdicionadoDTO criaItemVendaAddDTO(UUID produtoId) {
        return new ItemVendaAdicionadoDTO(produtoId,30,BigDecimal.valueOf(12.90));
    }

    private VendaResponseDTO criarVendaResponseEsperado() {
        return new VendaResponseDTO(
                vendaId,
                Instant.now(),
                BigDecimal.valueOf(258),
                StatusVenda.ABERTA,
                new SedeResponseDTO(sedeId, "Sede Teste", "Rua A", "Bairro A", "123", "Cidade X", "UF", "PRINCIPAL"),
                new FormatoVendaDTO(formatoId, TipoFormatoVenda.FISICA),
                List.of()
        );
    }

    private VendaResponseDTO vendaAprovadaResponseEsperado() {
        return new VendaResponseDTO(
                vendaId,
                Instant.now(),
                BigDecimal.valueOf(258),
                StatusVenda.APROVADA,
                new SedeResponseDTO(sedeId, "Sede Teste", "Rua A", "Bairro A", "123", "Cidade X", "UF", "PRINCIPAL"),
                new FormatoVendaDTO(formatoId, TipoFormatoVenda.FISICA),
                List.of()
        );
    }

    private VendaResponseDTO vendaCanceladaResponseEsperado() {
        return new VendaResponseDTO(
                vendaId,
                Instant.now(),
                BigDecimal.valueOf(258),
                StatusVenda.CANCELADA,
                new SedeResponseDTO(sedeId, "Sede Teste", "Rua A", "Bairro A", "123", "Cidade X", "UF", "PRINCIPAL"),
                new FormatoVendaDTO(formatoId, TipoFormatoVenda.FISICA),
                List.of()
        );
    }

    private Produto criaProduto(UUID id, Fornecedor fornecedor,List<ItemVenda> itens,List<EstoqueProduto> estoque ){
        return new Produto(id,"Uva Roxa","Caixa",BigDecimal.valueOf(12.90),fornecedor,itens,estoque);
    }

    private ItemVenda criaItemVenda(UUID id, Venda venda, Produto produto){
        return new ItemVenda(itemVendaId, 20,BigDecimal.valueOf(12.90),venda,produto);
    }

    private EstoqueProduto criaEstoqueProduto(UUID id,Produto produto,Sede sede) {
        return new EstoqueProduto(id,produto,sede,30);

    }

    private Fornecedor criaForncedor(UUID id, Set<Produto> produtos){
        return new Fornecedor(id,"Fazenda 123","Pouso Alegre","MG","238468268234","31 999990000","fazenda@email.com",true,produtos);

    }

    private void mockRepositorios(FormatoVenda formato, Sede sede, Venda venda, BalanceteOperacaoVenda balancete) {
        when(formatoVendaRepository.findById(formato.getId())).thenReturn(Optional.of(formato));
        when(sedeRepository.findById(sede.getId())).thenReturn(Optional.of(sede));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);
        when(balanceteOperacaoVendaRepository.save(any())).thenReturn(balancete);
    }
}