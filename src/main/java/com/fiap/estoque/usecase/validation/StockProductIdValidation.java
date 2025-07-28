package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.InvalidProductIdException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StockProductIdValidation implements CreateStockStrategy, UpdateStockStrategy {

    @Override
    public void validate(Stock stock) {
        if (!stock.hasValidProductId()) {
            throw new InvalidProductIdException();
        }
    }
}
