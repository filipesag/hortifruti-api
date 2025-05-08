package com.hortifruti.hortifrutiapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "forma_pagamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    private String descricao;

    private Double taxa;

    private BigDecimal valorTaxa;

    @OneToMany(mappedBy = "formaPagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Receita> receitas = new HashSet<>();

}
