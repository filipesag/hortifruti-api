package com.hortifruti.hortifrutiapi.repository;

import com.hortifruti.hortifrutiapi.dto.produto.ProdutoEstoqueDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutosEmSedeDTO;
import com.hortifruti.hortifrutiapi.model.EstoqueProduto;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.model.Produto;
import com.hortifruti.hortifrutiapi.model.Sede;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class ProdutoRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void setUp(){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(UUID.randomUUID());
        fornecedor.setCnpj("95777462000126");
        fornecedor.setEstado("Minas Gerais");
        fornecedor.setNome("Fazenda Monte Alegre");
        fornecedor.setEmail("fazenda@hotmail.com");
        fornecedor.setCidade("Esmeraldas");
        fornecedor.setIsAtivo(true);
        entityManager.merge(fornecedor);

        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setNome("Maçã");
        produto.setUnidadeMedida("KG");
        produto.setPreco(BigDecimal.valueOf(34.00));
        produto.setFornecedor(fornecedor);
        entityManager.merge(produto);

        Sede sede = new Sede();
        sede.setId(UUID.randomUUID());
        sede.setNome("Sede Central");
        sede.setRua("Rua Teste");
        sede.setEstado("RJ");
        sede.setNumero("123");
        sede.setBairro("Tijuca");
        sede.setDescricao("Loja da tijuca");
        entityManager.merge(sede);

        EstoqueProduto estoqueProduto = new EstoqueProduto();
        estoqueProduto.setId(UUID.randomUUID());
        estoqueProduto.setProduto(produto);
        estoqueProduto.setSede(sede);
        estoqueProduto.setQuantidade(50);
        entityManager.merge(estoqueProduto);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("Deve retornar a sede com estoque do produto informado")
    void buscaSedeComEstoqueDeProdutoSucesso() {
        List<ProdutoEstoqueDTO> resultado = produtoRepository.buscaSedeComEstoqueDeProduto("Maçã");

        assertThat(resultado).hasSize(1);
        assertThat(resultado).isNotEmpty();
        ProdutoEstoqueDTO dto = resultado.get(0);
        assertThat(dto.getNomeProduto()).isEqualTo("Maçã");
        assertThat(dto.getUnidadeMedida()).isEqualTo("KG");
        assertThat(dto.getQuantidade()).isEqualTo(50);
        assertThat(dto.getNomeSede()).isEqualTo("Sede Central");
    }

    @Test
    void buscaProdutosEmSede() {
        List<ProdutosEmSedeDTO> resultado = produtoRepository.buscaProdutosEmSede("Sede Central");

        assertThat(resultado).hasSize(1);
        assertThat(resultado).isNotEmpty();
        ProdutosEmSedeDTO dto = resultado.get(0);
        assertThat(dto.getNomeProduto()).isEqualTo("Maçã");
        assertThat(dto.getQuantidade()).isEqualTo(50);
        assertThat(dto.getUnidadeMedida()).isEqualTo("KG");
    }
}