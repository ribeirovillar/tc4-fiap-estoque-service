package com.fiap.estoque.exception;

public class InvalidStockQuantityException extends RuntimeException {
    public InvalidStockQuantityException() {
        super("Quantity must be >= 0");
    }
}
