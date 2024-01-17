package com.example.order_info_micro.exception.server;

public class InvalidOrderInfoDetailsException extends RuntimeException {
    public InvalidOrderInfoDetailsException() {
    }

    public InvalidOrderInfoDetailsException(String message) {
        super(message);
    }
}
