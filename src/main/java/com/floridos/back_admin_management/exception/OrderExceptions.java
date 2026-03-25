package com.floridos.back_admin_management.exception;

public class OrderExceptions {
    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException() {
            super("Order not found");
        }

        public OrderNotFoundException(String message) {
            super(message);
        }

        public OrderNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        public OrderNotFoundException(Throwable cause) {
            super(cause);
        }
    }
}
