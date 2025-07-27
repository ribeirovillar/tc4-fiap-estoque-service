package com.fiap.estoque.infra.handler;

import com.fiap.estoque.exception.StockProductAlreadyRegisteredException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StockProductAlreadyRegisteredExceptionMapper implements ExceptionMapper<StockProductAlreadyRegisteredException> {

    @Override
    public Response toResponse(StockProductAlreadyRegisteredException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "STOCK_PRODUCT_ALREADY_REGISTERED",
                exception.getMessage(),
                Response.Status.CONFLICT.getStatusCode()
        );

        return Response.status(Response.Status.CONFLICT)
                .entity(errorResponse)
                .build();
    }
}