package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.StockProductNotFoundException;
import com.fiap.estoque.gateway.StockGateway;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class StockProductMissingValidation implements UpdateStockStrategy {

    private final StockGateway stockGateway;


    public StockProductMissingValidation(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Override
    public void validate(Stock stock) {
        if (Objects.nonNull(stock) && Objects.nonNull(stock.getProductId())) {
            stockGateway.findByProductId(stock.getProductId()).orElseThrow(() -> new StockProductNotFoundException("Stock not found for productId: " + stock.getProductId()));
        }
    }
}
