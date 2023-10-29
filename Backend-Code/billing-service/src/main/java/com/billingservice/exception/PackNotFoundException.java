package com.billingservice.exception;

public class PackNotFoundException extends RuntimeException {
    public PackNotFoundException(String message) {
        super(message);
    }
}