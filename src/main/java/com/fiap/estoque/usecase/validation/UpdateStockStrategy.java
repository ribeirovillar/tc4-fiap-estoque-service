package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;

public interface UpdateStockStrategy {
    void validate(Stock stock);
}
