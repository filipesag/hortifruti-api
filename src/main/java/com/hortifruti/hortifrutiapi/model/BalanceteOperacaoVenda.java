package com.hortifruti.hortifrutiapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "receita")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceteOperacaoVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "data_receita")
    private Date dataReceita;

    @Column(name = "valor_receita")
    private BigDecimal valorReceita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

}
