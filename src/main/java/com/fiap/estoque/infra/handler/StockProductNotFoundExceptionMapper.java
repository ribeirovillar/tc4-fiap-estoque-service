package com.fiap.estoque.infra.handler;

import com.fiap.estoque.exception.StockProductNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StockProductNotFoundExceptionMapper implements ExceptionMapper<StockProductNotFoundException> {

    @Override
    public Response toResponse(StockProductNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "STOCK_PRODUCT_NOT_FOUND",
                exception.getMessage(),
                Response.Status.NOT_FOUND.getStatusCode()
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .build();
    }
}