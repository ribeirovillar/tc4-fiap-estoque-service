package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.StockProductNotFoundException;
import com.fiap.estoque.gateway.StockGateway;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class GetStockByProductIdUseCase {

    private final StockGateway gateway;

    public GetStockByProductIdUseCase(StockGateway gateway) {
        this.gateway = gateway;
    }

    public Stock execute(UUID productId) {
        return gateway.findByProductId(productId)
                .orElseThrow(() -> new StockProductNotFoundException("Stock not found for productId: " + productId));
    }
}
