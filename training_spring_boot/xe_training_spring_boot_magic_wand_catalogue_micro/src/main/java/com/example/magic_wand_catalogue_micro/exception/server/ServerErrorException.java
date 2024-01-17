package com.example.magic_wand_catalogue_micro.exception.server;

public class ServerErrorException extends RuntimeException {
    public ServerErrorException() {
    }

    public ServerErrorException(String message) {
        super(message);
    }
}
