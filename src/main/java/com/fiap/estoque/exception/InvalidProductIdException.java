package com.fiap.estoque.exception;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException() {
        super("ProductId cannot be null");
    }
}
