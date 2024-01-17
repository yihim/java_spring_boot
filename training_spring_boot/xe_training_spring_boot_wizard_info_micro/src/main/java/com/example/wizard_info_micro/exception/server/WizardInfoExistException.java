package com.example.wizard_info_micro.exception.server;

public class WizardInfoExistException extends RuntimeException {
    public WizardInfoExistException() {
    }

    public WizardInfoExistException(String message) {
        super(message);
    }
}
