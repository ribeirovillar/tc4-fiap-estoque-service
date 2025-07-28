package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.gateway.StockGateway;
import com.fiap.estoque.usecase.validation.CreateStockStrategy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateStockUseCase {

    private final StockGateway gateway;
    private final Instance<CreateStockStrategy> createStockStrategies;

    public CreateStockUseCase(StockGateway gateway, Instance<CreateStockStrategy> createStockStrategies) {
        this.gateway = gateway;
        this.createStockStrategies = createStockStrategies;
    }

    @Transactional
    public Stock execute(Stock stock) {
        createStockStrategies.forEach(strategy -> strategy.validate(stock));
        return gateway.save(stock);
    }
}
