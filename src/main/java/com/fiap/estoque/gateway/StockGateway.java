package com.fiap.estoque.gateway;



import com.fiap.estoque.domain.Stock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockGateway {
    Stock save(Stock stock);
    Stock update(Stock stock);
    Optional<Stock> findByProductId(UUID productId);
    List<Stock> findAll();
    void deleteByProductId(UUID productId);
}
