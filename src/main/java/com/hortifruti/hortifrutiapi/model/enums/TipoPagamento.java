package com.hortifruti.hortifrutiapi.model.enums;

public enum TipoPagamento {
    DINHEIRO("Dinheiro", 0.0),
    PIX("Pix", 0.0),
    CARTAO_DEBITO_GOLDMONEY("Cartão de Débito GoldMoney", 2.22),
    CARTAO_DEBITO_VIDACARD("Cartão de Débito VidaCard", 2.99),
    CARTAO_DEBITO_MASTERCOIN("Cartão de Débito MasterCoin", 1.93),
    CARTAO_CREDITO_MASTERCOIN("Cartão de Crédito MasterCoin",2.13),
    CARTAO_CREDITO_GOLDMONEY("Cartão de Crédito GoldMoney", 2.33),
    CARTAO_CREDITO_VIDACARD("Cartão de Crédito VidaCard", 3.02),
    CARTAO_ALIMENTACAO_ALEXO("Cartão Alimentação Alexo", 1.77),
    CARTAO_ALIMENTACAO_SODELO("Cartão Alimentação Sodelo", 2.21),
    CARTAO_REFEICAO_ALEXO("Cartão Refeição Alexo", 1.99),
    CARTAO_REFEICAO_SODELO("Cartão Refeição Sodelo", 3.50);

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
