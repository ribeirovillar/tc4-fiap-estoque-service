package com.fiap.estoque.exception;

public class StockProductNotFoundException extends RuntimeException {
    public StockProductNotFoundException(String message) {
        super(message);
    }
}
