package com.fiap.estoque.exception;

public class StockProductAlreadyRegisteredException extends RuntimeException {
    public StockProductAlreadyRegisteredException() {
        super("Product already registered in stock");
    }
}
