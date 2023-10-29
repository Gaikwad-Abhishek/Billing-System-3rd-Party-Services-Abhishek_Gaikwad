package com.collectionservice.exception;

public class SuspendedAccountNotFoundException extends RuntimeException {
    public SuspendedAccountNotFoundException(String message) {
        super(message);
    }
}