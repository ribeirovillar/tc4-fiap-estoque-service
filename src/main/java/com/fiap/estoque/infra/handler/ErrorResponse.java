package com.fiap.estoque.infra.handler;

public record ErrorResponse(String errorCode, String message, int status) {
}
