package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.gateway.StockGateway;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ListAllStockUseCase {

    private final StockGateway gateway;

    public ListAllStockUseCase(StockGateway gateway) {
        this.gateway = gateway;
    }

    public List<Stock> execute() {
        return gateway.findAll();
    }
}
