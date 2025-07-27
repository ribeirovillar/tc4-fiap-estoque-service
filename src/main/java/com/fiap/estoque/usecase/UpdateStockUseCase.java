package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.StockProductNotFoundException;
import com.fiap.estoque.gateway.StockGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class UpdateStockUseCase {

    private final StockGateway gateway;

    public UpdateStockUseCase(StockGateway gateway) {
        this.gateway = gateway;
    }

    @Transactional
    public Stock execute(UUID productId, Integer newQuantity) {
        Stock stock = gateway.findByProductId(productId)
                .orElseThrow(() -> new StockProductNotFoundException("Stock not found for productId: " + productId));
        stock.setQuantity(newQuantity);
        return gateway.update(stock);
    }
}
