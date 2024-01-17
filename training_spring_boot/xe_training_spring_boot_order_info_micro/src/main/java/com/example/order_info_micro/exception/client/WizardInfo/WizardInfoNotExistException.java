package com.example.order_info_micro.exception.client.WizardInfo;

public class WizardInfoNotExistException extends RuntimeException {
    private String message;
    private int httpStatus;

    public WizardInfoNotExistException() {
    }

    public WizardInfoNotExistException(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
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
