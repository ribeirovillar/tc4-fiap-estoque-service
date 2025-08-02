package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.gateway.StockGateway;
import com.fiap.estoque.usecase.validation.ReverseStockStrategy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BatchStockReversalUseCase {


    private final StockGateway stockGateway;
    private final Instance<ReverseStockStrategy> reverseStockStrategyInstance;

    public BatchStockReversalUseCase(StockGateway stockGateway, Instance<ReverseStockStrategy> reverseStockStrategyInstance) {
        this.stockGateway = stockGateway;
        this.reverseStockStrategyInstance = reverseStockStrategyInstance;
    }


    @Transactional
    public List<Stock> execute(List<Stock> stocksToReverse) {
        List<Stock> updatedStocks = new ArrayList<>();

        reverseStockStrategyInstance.forEach(strategy -> strategy.validate(stocksToReverse));

        for (Stock stockToReverse : stocksToReverse) {
            stockGateway.findByProductId(stockToReverse.getProductId())
                    .ifPresent(currentStock -> {
                        currentStock.setQuantity(currentStock.getQuantity() + stockToReverse.getQuantity());
                        updatedStocks.add(stockGateway.update(currentStock));
                    });
        }

        return updatedStocks;
    }
}
