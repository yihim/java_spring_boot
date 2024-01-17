package com.example.order_info_micro.exception.client.MagicWandCatalogue;

public class MagicWandCatalogueNotValidException extends RuntimeException {

    private String message;
    private int httpStatus;

    public MagicWandCatalogueNotValidException() {
    }

    public MagicWandCatalogueNotValidException(String message, int httpStatus) {
        setMessage(message);
        setHttpStatus(httpStatus);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
