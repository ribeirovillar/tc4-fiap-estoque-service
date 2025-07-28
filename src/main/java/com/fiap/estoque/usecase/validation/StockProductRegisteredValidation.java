package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.StockProductAlreadyRegisteredException;
import com.fiap.estoque.gateway.StockGateway;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class StockProductRegisteredValidation implements CreateStockStrategy {

    private final StockGateway stockGateway;

    public StockProductRegisteredValidation(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Override
    public void validate(Stock stock) {
        if (Objects.isNull(stock) || Objects.isNull(stock.getProductId())) {
            return;
        }
        stockGateway.findByProductId(stock.getProductId()).ifPresent(existingStock -> {
            throw new StockProductAlreadyRegisteredException();
        });
    }
}
