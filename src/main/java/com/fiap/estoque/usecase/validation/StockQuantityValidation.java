package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.InvalidStockQuantityException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class StockQuantityValidation implements CreateStockStrategy, UpdateStockStrategy, DeductionStockStrategy, ReverseStockStrategy {

    @Override
    public void validate(List<Stock> stocks) {
        for (Stock stockToDeduct : stocks) {
            validate(stockToDeduct);
        }
    }

    @Override
    public void validate(Stock stock) {
        if (!stock.hasValidQuantity()) {
            throw new InvalidStockQuantityException();
        }
    }
}
