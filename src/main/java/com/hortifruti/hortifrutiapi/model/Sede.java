package com.hortifruti.hortifrutiapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "sede")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Sede implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;

    private String bairro;

    private String cidade;

    private String estado;

    private String rua;

    private String numero;

    private String descricao;

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendas = new ArrayList<>();

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueProduto> estoques = new ArrayList<>();

}
