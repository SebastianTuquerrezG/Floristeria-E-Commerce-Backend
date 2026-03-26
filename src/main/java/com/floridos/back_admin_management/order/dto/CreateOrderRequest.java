package com.floridos.back_admin_management.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String contactName;

    @NotBlank(message = "El teléfono es obligatorio")
    private String contactPhone;

    private String contactEmail;
    private String contactCity;
    private String notes;
    private String couponCode;

    @NotBlank(message = "El método de pago es obligatorio")
    private String paymentMethod;   // card | nequi | pse | whatsapp

    @NotEmpty(message = "Debe haber al menos un grupo de entrega")
    @Valid
    private List<DeliveryGroupRequest> deliveryGroups;
}
