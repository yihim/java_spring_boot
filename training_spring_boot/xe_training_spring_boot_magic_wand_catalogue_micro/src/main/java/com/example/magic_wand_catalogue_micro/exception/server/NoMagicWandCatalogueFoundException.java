package com.example.magic_wand_catalogue_micro.exception.server;

public class NoMagicWandCatalogueFoundException extends RuntimeException {
    public NoMagicWandCatalogueFoundException() {
    }

    public NoMagicWandCatalogueFoundException(String message) {
        super(message);
    }
}
