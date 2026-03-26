package com.floridos.back_admin_management.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class DeliveryGroupRequest {
    @NotBlank
    private String groupName;

    private String fragrance;
    private String cardMessage;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    @NotNull(message = "La fecha de entrega es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser en el pasado")
    private LocalDate deliveryDate;

    private LocalTime deliveryTime;

    @NotEmpty(message = "El grupo debe tener al menos un producto")
    @Valid
    private List<OrderItemRequest> items;
}
