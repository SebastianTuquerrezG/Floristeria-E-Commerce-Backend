package com.floridos.back_admin_management.order;


public enum OrderStatus {
        PENDING,    // pedido creado pero no confirmado
        CONFIRMED,  // pedido confirmado por el cliente
        PREPARING,  // en preparación
        SHIPPED,    // enviado
        DELIVERED,  // entregado
        CANCELLED   // cancelado
}
