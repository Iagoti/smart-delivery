package br.com.iago.smartdelivery.modules.products.dto;

import java.util.UUID;

public record CreateProductResponse(int code, String name, UUID id) {
}
