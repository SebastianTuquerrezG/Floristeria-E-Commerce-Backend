package com.floridos.back_admin_management.exception;

public class OrderExceptions {
    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException() {
            super("Orden no encontrada");
        }
        public OrderNotFoundException(String message) {
            super(message);
        }
        public OrderNotFoundException(Long id) {
            super("Orden no encontrada con id: " + id);
        }
    }

    public static class InvalidStatusException extends RuntimeException {
        public InvalidStatusException(String status) {
            super("Estado inválido: " + status);
        }
    }
}
