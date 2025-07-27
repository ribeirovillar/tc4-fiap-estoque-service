package com.fiap.estoque.infra.handler;

import com.fiap.estoque.exception.InvalidStockQuantityException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidStockQuantityExceptionMapper implements ExceptionMapper<InvalidStockQuantityException> {

    @Override
    public Response toResponse(InvalidStockQuantityException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INVALID_STOCK_QUANTITY",
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .build();
    }
}