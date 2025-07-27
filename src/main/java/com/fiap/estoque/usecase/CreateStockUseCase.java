package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.exception.StockProductAlreadyRegisteredException;
import com.fiap.estoque.gateway.StockGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateStockUseCase {

    private final StockGateway gateway;

    public CreateStockUseCase(StockGateway gateway) {
        this.gateway = gateway;
    }

    @Transactional
    public Stock execute(Stock stock) {
        gateway.findByProductId(stock.getProductId()).ifPresent(existingStock -> {
            throw new StockProductAlreadyRegisteredException();
        });
        return gateway.save(stock);
    }
}
