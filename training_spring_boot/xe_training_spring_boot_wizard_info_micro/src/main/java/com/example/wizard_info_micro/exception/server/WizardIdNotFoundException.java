package com.example.wizard_info_micro.exception.server;

public class WizardIdNotFoundException extends RuntimeException {

    public WizardIdNotFoundException() {
    }

    public WizardIdNotFoundException(String message) {
        super(message);
    }
}
