package com.hortfruit.hortfruitapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;
    @Column(name="data_venda")
    private Date dataVenda;
    private BigDecimal total;
    @Column(name="status_venda")
    private String statusVenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id", nullable = false)
    private Receita receita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_entrega_id", nullable = false)
    private EnderecoEntrega enderecoEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formato_venda_id", nullable = false)
    private FormatoVenda formatoVenda;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itens;
}
