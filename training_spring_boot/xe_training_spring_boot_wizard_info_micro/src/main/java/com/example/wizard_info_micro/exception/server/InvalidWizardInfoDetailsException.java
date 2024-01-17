package com.example.wizard_info_micro.exception.server;

public class InvalidWizardInfoDetailsException extends RuntimeException {
    public InvalidWizardInfoDetailsException() {
    }

    public InvalidWizardInfoDetailsException(String message) {
        super(message);
    }
}
