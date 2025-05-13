package com.hortifruti.hortifrutiapi.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "sede")
@Data
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
    @JsonManagedReference
    private List<Venda> vendas = new ArrayList<>();

    @OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstoqueProduto> estoques = new ArrayList<>();

}
