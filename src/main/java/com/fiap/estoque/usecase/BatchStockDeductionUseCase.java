package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.gateway.StockGateway;
import com.fiap.estoque.usecase.validation.DeductionStockStrategy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BatchStockDeductionUseCase {

    private final StockGateway stockGateway;
    private final Instance<DeductionStockStrategy> deductionStockStrategyInstance;

    public BatchStockDeductionUseCase(StockGateway stockGateway, Instance<DeductionStockStrategy> deductionStockStrategyInstance) {
        this.stockGateway = stockGateway;
        this.deductionStockStrategyInstance = deductionStockStrategyInstance;
    }

    @Transactional
    public List<Stock> execute(List<Stock> stocksToDeduct) {

        deductionStockStrategyInstance.forEach(strategy -> strategy.validate(stocksToDeduct));

        List<Stock> updatedStocks = new ArrayList<>();
        for (Stock stockToDeduct : stocksToDeduct) {
            stockGateway.findByProductId(stockToDeduct.getProductId())
                    .ifPresent(currentStock -> {
                        currentStock.setQuantity(currentStock.getQuantity() - stockToDeduct.getQuantity());
                        updatedStocks.add(stockGateway.update(currentStock));
                    });
        }

        return updatedStocks;
    }
}
