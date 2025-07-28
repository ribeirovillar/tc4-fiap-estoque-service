package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.gateway.StockGateway;
import com.fiap.estoque.usecase.validation.UpdateStockStrategy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class UpdateStockUseCase {

    private final StockGateway gateway;
    private final Instance<UpdateStockStrategy> updateStockStrategies;

    public UpdateStockUseCase(StockGateway gateway, Instance<UpdateStockStrategy> updateStockStrategies) {
        this.gateway = gateway;
        this.updateStockStrategies = updateStockStrategies;
    }

    @Transactional
    public Stock execute(UUID productId, Integer newQuantity) {
        updateStockStrategies.forEach(strategy -> strategy.validate(new Stock(productId, newQuantity)));
        gateway.findByProductId(productId)
                .ifPresent(stock -> {
                    stock.setQuantity(newQuantity);
                    gateway.update(stock);
                });

        return gateway.findByProductId(productId).orElse(new Stock(productId, newQuantity));
    }
}
