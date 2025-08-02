package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.InsufficientStockException;
import com.fiap.estoque.exception.StockProductNotFoundException;
import com.fiap.estoque.gateway.StockGateway;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SufficientStockValidation implements DeductionStockStrategy {

    private final StockGateway stockGateway;

    public SufficientStockValidation(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Override
    public void validate(List<Stock> stocksToDeduct) {
        for (Stock stockToDeduct : stocksToDeduct) {
            Optional<Stock> currentStockOpt = stockGateway.findByProductId(stockToDeduct.getProductId());
            if (currentStockOpt.isEmpty()) {
                throw new StockProductNotFoundException("Stock not found for productId: " + stockToDeduct.getProductId());
            }

            Stock currentStock = currentStockOpt.get();
            if (currentStock.getQuantity() < stockToDeduct.getQuantity()) {
                throw new InsufficientStockException(
                        stockToDeduct.getProductId(),
                        currentStock.getQuantity(),
                        stockToDeduct.getQuantity()
                );
            }
        }
    }
}
