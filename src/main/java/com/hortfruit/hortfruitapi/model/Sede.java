package com.hortfruit.hortfruitapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sede")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sede implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    private String nome;

    private String bairro;

    private String cidade;

    private String estado;

    private String rua;

    private String numero;

    private String descricao;

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venda> sedeLista = new HashSet<>();

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> vendas;

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueProduto> estoques;

}
