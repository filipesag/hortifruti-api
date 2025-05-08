package com.hortfruit.hortfruitapi.model;

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
@Table(name = "formato_venda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormatoVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    private String tipo;

    @OneToMany(mappedBy = "formatoVenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venda> formatoVendas = new HashSet<>();
}
