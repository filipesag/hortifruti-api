package com.hortifruti.hortifrutiapi.model;

import com.hortifruti.hortifrutiapi.model.enums.TipoFormatoVenda;
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

    @Enumerated(EnumType.STRING)
    private TipoFormatoVenda tipo;

    @OneToMany(mappedBy = "tipo_venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venda> tipo_venda = new HashSet<>();
}
