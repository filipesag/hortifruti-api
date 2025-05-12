package com.hortifruti.hortifrutiapi.model.enums;

public enum TipoFormatoVenda {

    FISICA("Venda em loja física"),
    APP_QUICK_FOOD("Venda pelo aplicativo Quick Food"),
    APP_RANGONTIME("Venda pelo aplicativo Rangontime"),
    APP_NATIVO_HORTIFRUTI("Venda pelo aplicativo Hortifruti"),
    ENCOMENDA_CONTRATO("Venda contratual"),
    RETIRADA("Venda com retirada no balcão");

    private final String descricao;

    TipoFormatoVenda(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
