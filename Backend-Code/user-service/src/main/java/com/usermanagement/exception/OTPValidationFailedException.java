package com.usermanagement.exception;

public class OTPValidationFailedException extends RuntimeException {

    public OTPValidationFailedException() {
        super("OTP validation failed.");
    }

    public OTPValidationFailedException(String message) {
        super(message);
    }

    public OTPValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

