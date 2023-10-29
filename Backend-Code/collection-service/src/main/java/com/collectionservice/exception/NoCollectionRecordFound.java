package com.collectionservice.exception;

public class NoCollectionRecordFound extends RuntimeException {
    public NoCollectionRecordFound(String message) {
        super(message);
    }
}