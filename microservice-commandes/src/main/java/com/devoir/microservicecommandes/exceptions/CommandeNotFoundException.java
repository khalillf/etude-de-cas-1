package com.devoir.microservicecommandes.exceptions;

public class CommandeNotFoundException extends RuntimeException {
    public CommandeNotFoundException(String message) {
        super(message);
    }
}