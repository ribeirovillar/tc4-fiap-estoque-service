package com.fiap.estoque.gateway.database.jpa;

import com.fiap.estoque.domain.Stock;
import com.fiap.estoque.gateway.StockGateway;
import com.fiap.estoque.gateway.database.jpa.entity.StockEntity;
import com.fiap.estoque.gateway.database.jpa.repository.StockRepository;
import com.fiap.estoque.mapper.StockMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class StockJpaGateway implements StockGateway {

    private final StockRepository repository;
    private final StockMapper mapper;

    public StockJpaGateway(StockRepository repository, StockMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Stock save(Stock stock) {
        StockEntity entity = mapper.toEntity(stock);
        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Stock update(Stock stock) {
        StockEntity entity = mapper.toEntity(stock);
        repository.getEntityManager().merge(entity);
        return mapper.toDomain(entity);
    }


    @Override
    public Optional<Stock> findByProductId(UUID productId) {
        return repository.findByProductId(productId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Stock> findAll() {
        return repository.listAll()
                .stream().map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByProductId(UUID productId) {
        repository.findByProductId(productId).ifPresent(repository::delete);
    }
}
