package com.fiap.estoque.controller;


import com.fiap.estoque.controller.json.StockDTO;
import com.fiap.estoque.mapper.StockMapper;
import com.fiap.estoque.usecase.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/stocks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StockController {

    @Inject
    StockMapper mapper;
    @Inject
    CreateStockUseCase createUseCase;
    @Inject
    UpdateStockUseCase updateUseCase;
    @Inject
    GetStockByProductIdUseCase getByIdUseCase;
    @Inject
    ListAllStockUseCase listAllUseCase;
    @Inject
    DeleteStockUseCase deleteUseCase;

    @GET
    public Response list() {
        return Response
                .status(Response.Status.OK)
                .entity(listAllUseCase.execute().stream().map(mapper::toDTO).toList())
                .build();
    }

    @GET
    @Path("/products/{productId}")
    public Response get(@PathParam("productId") UUID productId) {
        return Response
                .status(Response.Status.OK)
                .entity(mapper.toDTO(getByIdUseCase.execute(productId)))
                .build();
    }

    @POST
    public Response create(StockDTO dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(mapper.toDTO(createUseCase.execute(mapper.toDomain(dto))))
                .build();
    }

    @PUT
    @Path("/products/{productId}")
    public Response update(@PathParam("productId") UUID productId, StockDTO dto) {
        return Response
                .status(Response.Status.OK.getStatusCode())
                .entity(mapper.toDTO(updateUseCase.execute(productId, dto.getQuantity())))
                .build();
    }

    @DELETE
    @Path("/products/{productId}")
    public Response delete(@PathParam("productId") UUID productId) {
        deleteUseCase.execute(productId);
        return Response.noContent().build();
    }
}
