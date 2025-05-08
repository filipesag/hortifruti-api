package com.hortfrut.hortfrutapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
@Entity
@Table(name="fornecedor")
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String cidade;
    private String estado;
    private String cnpj;
    private String telefone;
    private String email;




}
