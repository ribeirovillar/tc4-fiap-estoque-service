package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.InvalidProductIdException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class StockProductIdValidation implements CreateStockStrategy, UpdateStockStrategy, DeductionStockStrategy, ReverseStockStrategy {

    @Override
    public void validate(Stock stock) {
        if (!stock.hasValidProductId()) {
            throw new InvalidProductIdException();
        }
    }

    @Override
    public void validate(List<Stock> stocks) {
        for (Stock stock : stocks) {
            validate(stock);
        }
    }
}
