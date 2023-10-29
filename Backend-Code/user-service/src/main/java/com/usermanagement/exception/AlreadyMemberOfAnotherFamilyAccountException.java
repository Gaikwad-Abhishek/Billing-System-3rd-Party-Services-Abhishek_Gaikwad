package com.usermanagement.exception;

public class AlreadyMemberOfAnotherFamilyAccountException extends RuntimeException {
    public AlreadyMemberOfAnotherFamilyAccountException(String message) {
        super(message);
    }
}