package com.usermanagement.exception;

public class FamilyAccountNotFoundException extends RuntimeException {
    public FamilyAccountNotFoundException(String message) {
        super(message);
    }
}