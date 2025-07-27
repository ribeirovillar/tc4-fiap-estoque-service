package com.fiap.estoque.gateway.database.jpa.repository;

import com.fiap.estoque.gateway.database.jpa.entity.StockEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class StockRepository implements PanacheRepository<StockEntity> {
    public Optional<StockEntity> findByProductId(UUID productId) {
        return find("productId", productId).firstResultOptional();
    }

}
