package com.hortifruti.hortifrutiapi.model.enums;

public enum StatusVenda {

    ABERTA("Aberta"),
    APROVADA("Aprovada"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusVenda(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
