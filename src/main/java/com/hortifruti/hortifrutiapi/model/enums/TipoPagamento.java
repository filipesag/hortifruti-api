package com.hortifruti.hortifrutiapi.model.enums;

public enum TipoPagamento {
    DINHEIRO("Dinheiro", 0.0),
    PIX("Pix", 0.0),
    CARTAO_DEBITO_GOLDMONEY("Cartão de Débito GoldMoney", 0.5),
    CARTAO_DEBITO_VIDACARD("Cartão de Débito VidaCard", 0.5),
    CARTAO_DEBITO_MASTERCOIN("Cartão de Débito MasterCoin", 0.5),
    CARTAO_DEBITO_ALECARD("Cartão de Débito AleCard", 0.5),
    CARTAO_CREDITO_GOLDMONEY("Cartão de Crédito GoldMoney", 1.5),
    CARTAO_CREDITO_VIDACARD("Cartão de Crédito VidaCard", 1.5),
    CARTAO_CREDITO_ALECARD("Cartão de Crédito AleCard", 1.5),
    CARTAO_ALIMENTACAO_ALEXO("Cartão Alimentação Alexo", 0.0),
    CARTAO_ALIMENTACAO_SODELO("Cartão Alimentação Sodelo", 0.0),
    CARTAO_REFEICAO_ALEXO("Cartão Refeição Alexo", 0.0),
    CARTAO_REFEICAO_SODELO("Cartão Refeição Sodelo", 0.0);

    private final String descricao;
    private final double taxa;

    TipoPagamento(String descricao, double taxa) {
        this.descricao = descricao;
        this.taxa = taxa;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getTaxa() {
        return taxa;
    }
}
