package com.example.order_info_micro.exception.server;

public class OrderInfoExistException extends RuntimeException {
    public OrderInfoExistException() {
    }

    public OrderInfoExistException(String message) {
        super(message);
    }
}
