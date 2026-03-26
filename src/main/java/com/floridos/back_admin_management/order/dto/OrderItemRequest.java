package com.floridos.back_admin_management.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {
    @NotNull(message = "El id del producto es obligatorio")
    private Long productId;

    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;
}
