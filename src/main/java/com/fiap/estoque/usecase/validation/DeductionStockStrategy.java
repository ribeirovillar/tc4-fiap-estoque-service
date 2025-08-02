package com.fiap.estoque.usecase.validation;

import com.fiap.estoque.domain.Stock;

import java.util.List;

public interface DeductionStockStrategy {

    void validate(List<Stock> stocksToDeduct);

}
