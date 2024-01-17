package com.example.order_info_micro.exception.client.WizardInfo;

public class WizardInfoNotValidException extends RuntimeException {

    private String message;
    private int httpStatus;

    public WizardInfoNotValidException() {
    }

    public WizardInfoNotValidException(String message, int httpStatus) {
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
