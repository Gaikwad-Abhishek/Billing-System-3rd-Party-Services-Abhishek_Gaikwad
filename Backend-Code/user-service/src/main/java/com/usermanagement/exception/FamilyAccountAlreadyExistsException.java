package com.usermanagement.exception;

public class FamilyAccountAlreadyExistsException extends RuntimeException {
    public FamilyAccountAlreadyExistsException(String message) {
        super(message);
    }
}