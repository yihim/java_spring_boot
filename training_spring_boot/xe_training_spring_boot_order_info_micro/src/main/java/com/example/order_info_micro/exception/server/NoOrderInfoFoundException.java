package com.example.order_info_micro.exception.server;

public class NoOrderInfoFoundException extends RuntimeException {
    public NoOrderInfoFoundException() {
    }

    public NoOrderInfoFoundException(String message) {
        super(message);
    }
}
