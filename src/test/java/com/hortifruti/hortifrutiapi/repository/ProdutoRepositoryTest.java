package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.dto.produto.ProdutoEstoqueDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutosEmSedeDTO;
import com.hortifruti.hortifrutiapi.model.EstoqueProduto;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.model.Produto;
import com.hortifruti.hortifrutiapi.model.Sede;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProdutoRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto produto;
    private Sede sede;

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

        produto = Produto.builder()
                .nome("Maçã")
                .unidadeMedida("KG")
                .preco(BigDecimal.valueOf(34.00))
                .fornecedor(fornecedor)
                .build();
        entityManager.persist(produto);

        sede = Sede.builder()
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

        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    @DisplayName("Deve retornar a sede com estoque do produto informado")
    void buscaSedeComEstoqueDeProduto() {
        List<ProdutoEstoqueDTO> resultado = produtoRepository.buscaSedeComEstoqueDeProduto("Maçã");

        assertThat(resultado)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    assertThat(dto.getNomeProduto()).isEqualTo("Maçã");
                    assertThat(dto.getUnidadeMedida()).isEqualTo("KG");
                    assertThat(dto.getQuantidade()).isEqualTo(50);
                    assertThat(dto.getNomeSede()).isEqualTo("Sede Central");
                });
    }

    @Test
    @DisplayName("Deve retornar produtos presentes em determinada Sede")
    void buscaProdutosEmSede() {
        List<ProdutosEmSedeDTO> resultado = produtoRepository.buscaProdutosEmSede("Sede Central");

        assertThat(resultado)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    assertThat(dto.getNomeProduto()).isEqualTo("Maçã");
                    assertThat(dto.getQuantidade()).isEqualTo(50);
                    assertThat(dto.getUnidadeMedida()).isEqualTo("KG");
                });
    }
}