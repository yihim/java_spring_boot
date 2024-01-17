package com.example.wizard_info_micro.exception.server;

public class ServerErrorException extends RuntimeException {
    public ServerErrorException() {
    }

    public ServerErrorException(String message) {
        super(message);
    }
}
