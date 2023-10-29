package com.billingservice.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateSubscriptionException extends DataIntegrityViolationException {
    public DuplicateSubscriptionException(String message) {
        super(message);
    }

    public DuplicateSubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }
}

