package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.dto.venda.VendasPorSedeDTO;
import com.hortifruti.hortifrutiapi.model.*;
import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;
import com.hortifruti.hortifrutiapi.model.enums.TipoFormatoVenda;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VendaRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VendaRepository vendaRepository;

    private Venda venda;

    private Instant dateOfToday = Instant.now();

    @BeforeEach
    void setUp() {
        Fornecedor fornecedor = Fornecedor.builder()
                .cnpj("95777462000126")
                .estado("Minas Gerais")
                .nome("Fazenda Monte Alegre")
                .email("fazenda@hotmail.com")
                .cidade("Esmeraldas")
                .isAtivo(true)
                .build();
        entityManager.persist(fornecedor);

        Produto produto = Produto.builder()
                .nome("Maçã")
                .unidadeMedida("KG")
                .preco(BigDecimal.valueOf(34.00))
                .fornecedor(fornecedor)
                .build();
        entityManager.persist(produto);

        Sede sede = Sede.builder()
                .nome("Sede Central")
                .rua("Rua Teste")
                .estado("RJ")
                .numero("123")
                .bairro("Tijuca")
                .descricao("Loja da tijuca")
                .build();
        entityManager.persist(sede);

        EstoqueProduto estoqueProduto = EstoqueProduto.builder()
                .produto(produto)
                .sede(sede)
                .quantidade(50.0)
                .build();
        entityManager.persist(estoqueProduto);

        FormatoVenda formatoVenda = FormatoVenda.builder()
                .tipo(TipoFormatoVenda.APP_QUICK_FOOD)
                .tipo_venda(null)
                .build();
        entityManager.persist(formatoVenda);

        List<ItemVenda> itens = new ArrayList<>();

        Venda venda = Venda.builder()
                .dataVenda(dateOfToday)
                .itens(itens)
                .total(BigDecimal.valueOf(4.50))
                .statusVenda(StatusVenda.APROVADA)
                .balanceteOperacaoVenda(null)
                .sede(sede)
                .tipo_venda(formatoVenda)
                .build();
        entityManager.persist(venda);

        ItemVenda item = ItemVenda.builder()
                .produto(produto)
                .venda(venda)
                .quantidade(3.0)
                .precoUnit(BigDecimal.valueOf(1.50))
                .build();
        itens.add(item);
        entityManager.persist(item);

        entityManager.flush();
    }


    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    @DisplayName("Deve retornar vendas de uma sede")
    void buscaSedeComEstoqueDeProduto() {
        List<VendasPorSedeDTO> resultado = vendaRepository.buscaVendasPorSede("Sede Central");

        assertThat(resultado)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    AssertionsForClassTypes.assertThat(dto.getNome()).isEqualTo("Sede Central");
                    AssertionsForClassTypes.assertThat(dto.getDataVenda().truncatedTo(ChronoUnit.MILLIS)).isEqualTo(dateOfToday.truncatedTo(ChronoUnit.MILLIS));
                    AssertionsForClassTypes.assertThat(dto.getStatusVenda()).isEqualTo(StatusVenda.APROVADA);
                    AssertionsForClassTypes.assertThat(dto.getTotal()).usingComparator(BigDecimal::compareTo).isEqualTo(BigDecimal.valueOf(4.50));
                });
    }

}