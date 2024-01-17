package com.example.order_info_micro.exception.server;

public class OrderInfoIdNotFoundException extends RuntimeException {
    public OrderInfoIdNotFoundException() {
    }

    public OrderInfoIdNotFoundException(String message) {
        super(message);
    }
}
