package com.fiap.estoque.domain;

import java.util.UUID;

public class Stock {

    private final UUID productId;
    private Integer quantity;

    public Stock(UUID productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public boolean hasValidProductId() {
        return productId != null;
    }

    public boolean hasValidQuantity() {
        return quantity != null && quantity >= 0;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
