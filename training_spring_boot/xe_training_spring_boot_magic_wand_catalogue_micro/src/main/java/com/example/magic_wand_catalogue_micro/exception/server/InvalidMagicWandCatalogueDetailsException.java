package com.example.magic_wand_catalogue_micro.exception.server;

public class InvalidMagicWandCatalogueDetailsException extends RuntimeException {
    public InvalidMagicWandCatalogueDetailsException() {
    }

    public InvalidMagicWandCatalogueDetailsException(String message) {
        super(message);
    }
}
