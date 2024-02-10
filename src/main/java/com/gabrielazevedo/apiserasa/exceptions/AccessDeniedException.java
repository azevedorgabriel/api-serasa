package com.gabrielazevedo.apiserasa.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("User does not have correct role!");
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
