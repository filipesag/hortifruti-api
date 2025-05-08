package com.hortifruti.hortifrutiapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "endereco_entrega")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoEntrega implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    private String cidade;

    private String estado;

    private String bairro;

    private String rua;

    private String numero;

    @OneToMany(mappedBy = "enderecoEntrega", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venda> enderecoLista = new HashSet<>();

}
