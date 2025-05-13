package com.hortifruti.hortifrutiapi.service.exceptions;

public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException() {
        super("Estoque insuficiente para este produto!");
    }

    public EstoqueInsuficienteException(String msg) {
        super(msg);
    }
}
