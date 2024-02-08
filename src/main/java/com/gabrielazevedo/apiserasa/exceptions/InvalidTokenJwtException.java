package com.gabrielazevedo.apiserasa.exceptions;

public class InvalidTokenJwtException extends RuntimeException {
    public InvalidTokenJwtException() {
        super("Invalid token!");
    }

    public InvalidTokenJwtException(String message) {
        super(message);
    }
}
