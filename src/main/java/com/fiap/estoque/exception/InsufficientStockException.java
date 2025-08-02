package com.fiap.estoque.exception;

import java.util.UUID;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(UUID productId, Integer availableQuantity, Integer requestedQuantity) {
        super(String.format("Insufficient stock for product %s. Available: %d, Requested: %d",
                productId, availableQuantity, requestedQuantity));
    }

}
