package com.usermanagement.exception;

public class ActiveFamilyAccountNotFoundException extends RuntimeException {
    public ActiveFamilyAccountNotFoundException(String message) {
        super(message);
    }
}
