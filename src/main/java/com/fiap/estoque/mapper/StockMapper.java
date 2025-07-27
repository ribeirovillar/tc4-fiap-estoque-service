package com.fiap.estoque.mapper;

import com.fiap.estoque.controller.json.StockDTO;
import com.fiap.estoque.domain.Stock;

import com.fiap.estoque.gateway.database.jpa.entity.StockEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface StockMapper {
    Stock toDomain(StockDTO dto);
    Stock toDomain(StockEntity entity);
    StockDTO toDTO(Stock domain);
    StockEntity toEntity(Stock domain);
}
