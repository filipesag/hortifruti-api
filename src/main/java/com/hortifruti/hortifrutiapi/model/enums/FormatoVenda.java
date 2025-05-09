package com.hortifruti.hortifrutiapi.model.enums;

public enum FormatoVenda {

    FISICA("Venda em loja física"),
    APP_QUICK_FOOD("Venda pelo App Quick Food"),
    APP_RANGONTIME("Venda pelo App Rangontime"),
    APP_NATIVO_HORTIFRUTI("Venda pelo aplicativo Hortifruti"),
    ENCOMENDA_CONTRATO("Venda contratual");

    private final String descricao;

    FormatoVenda(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
