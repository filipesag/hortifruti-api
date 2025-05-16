package com.hortifruti.hortifrutiapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;


    @Column(name="nome")
    private String nome;

    @Column(name="unidade_medida")
    private String unidadeMedida;

    @Column(name="preco")
    private BigDecimal preco;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    @JsonManagedReference
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itensVendidos = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueProduto> estoques = new ArrayList<>();

}
