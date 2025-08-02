package com.fiap.estoque.infra.handler;

import com.fiap.estoque.exception.InsufficientStockException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InsufficientStockExceptionMapper implements ExceptionMapper<InsufficientStockException> {

    @Override
    public Response toResponse(InsufficientStockException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INSUFFICIENT_STOCK",
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .build();
    }
}
