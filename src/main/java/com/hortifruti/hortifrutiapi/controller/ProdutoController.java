package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.dto.estoque.EstoqueProdutoRequestDTO;
import com.hortifruti.hortifrutiapi.dto.estoque.EstoqueProdutoResponseDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutoEstoqueDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutoRequestDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutoResponseDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutosEmSedeDTO;
import com.hortifruti.hortifrutiapi.model.Produto;
import com.hortifruti.hortifrutiapi.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<ProdutoResponseDTO> buscarTodos(){
        List<ProdutoResponseDTO> produtos = produtoService.buscarTodos();
        return produtos;
    }

    @PostMapping("/adiciona-produto")
    public ResponseEntity<ProdutoResponseDTO> inserirNovoProduto(@RequestBody @Valid ProdutoRequestDTO produto) {
        ProdutoResponseDTO novoProduto = produtoService.criarProduto(produto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoProduto.id())
                .toUri();
        return ResponseEntity.created(uri).body(novoProduto);
    }

    @PostMapping("/estoque")
    public ResponseEntity<EstoqueProdutoResponseDTO> controlaEstoque(@RequestBody @Valid EstoqueProdutoRequestDTO estoque) {
        EstoqueProdutoResponseDTO estoqueCriado = produtoService.adicionarEstoque(estoque);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(estoqueCriado.id())
                .toUri();
        return ResponseEntity.created(uri).body(estoqueCriado);
    }

    @GetMapping("/produtos-estoque")
    public List<ProdutoEstoqueDTO> buscaProdutoEstoqueEmSede(@RequestParam String nomeProduto) {
        List<ProdutoEstoqueDTO> produtoEstoque = produtoService.buscaEstoqueDeProdutoEmSede(nomeProduto);
        return produtoEstoque;
    }

    @GetMapping("/produtos-em-sede")
    public List<ProdutosEmSedeDTO> buscaProdutosEmSede(@RequestParam String nomeSede) {
        List<ProdutosEmSedeDTO> produtoEstoque = produtoService.buscaProdutosEmSede(nomeSede);
        return produtoEstoque;
    }
}
