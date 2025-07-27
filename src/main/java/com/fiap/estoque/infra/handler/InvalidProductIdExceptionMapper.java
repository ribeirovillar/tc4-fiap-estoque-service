package com.fiap.estoque.infra.handler;

import com.fiap.estoque.exception.InvalidProductIdException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidProductIdExceptionMapper implements ExceptionMapper<InvalidProductIdException> {

    @Override
    public Response toResponse(InvalidProductIdException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INVALID_PRODUCT_ID",
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .build();
    }
}