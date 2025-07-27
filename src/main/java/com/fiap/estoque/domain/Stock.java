package com.fiap.estoque.domain;

import com.fiap.estoque.exception.InvalidProductIdException;
import com.fiap.estoque.exception.InvalidStockQuantityException;

import java.util.UUID;

public class Stock {

    private final UUID productId;
    private Integer quantity;

    public Stock(UUID productId, Integer quantity) {
        if (productId == null) throw new InvalidProductIdException();
        if (quantity == null || quantity < 0) throw new InvalidStockQuantityException();
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity == null || quantity < 0) throw new InvalidStockQuantityException();
        this.quantity = quantity;
    }
}
