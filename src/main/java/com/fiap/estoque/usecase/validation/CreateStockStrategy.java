package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;

public interface CreateStockStrategy {
    void validate(Stock stock);
}
