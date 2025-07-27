package com.fiap.estoque.usecase;

import com.fiap.estoque.exception.StockProductNotFoundException;
import com.fiap.estoque.gateway.StockGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class DeleteStockUseCase {

    private final StockGateway gateway;

    public DeleteStockUseCase(StockGateway gateway) {
        this.gateway = gateway;
    }

    @Transactional
    public void execute(UUID productId) {
        gateway.findByProductId(productId).orElseThrow(() -> new StockProductNotFoundException("Product not found"));
        gateway.deleteByProductId(productId);
    }
}
