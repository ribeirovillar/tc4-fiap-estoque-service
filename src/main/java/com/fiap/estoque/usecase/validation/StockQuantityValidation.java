package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.InvalidStockQuantityException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StockQuantityValidation implements CreateStockStrategy, UpdateStockStrategy {
    @Override
    public void validate(Stock stock) {
        if (!stock.hasValidQuantity()) {
            throw new InvalidStockQuantityException();
        }
    }
}
