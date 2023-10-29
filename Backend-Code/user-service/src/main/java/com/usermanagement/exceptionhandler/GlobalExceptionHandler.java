package com.usermanagement.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.usermanagement.exception.AlreadyMemberOfAnotherFamilyAccountException;
import com.usermanagement.exception.EmailNotFoundException;
import com.usermanagement.exception.FamilyAccountAlreadyExistsException;
import com.usermanagement.exception.FamilyAccountNotFoundException;
import com.usermanagement.exception.OTPNotFoundException;
import com.usermanagement.exception.OTPValidationFailedException;
import com.usermanagement.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
    @ExceptionHandler(FamilyAccountAlreadyExistsException.class)
    public ResponseEntity<String> handleFamilyAccountAlreadyExistsException(FamilyAccountAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AlreadyMemberOfAnotherFamilyAccountException.class)
    public ResponseEntity<String> handleAlreadyMemberOfAnotherFamilyAccountException(AlreadyMemberOfAnotherFamilyAccountException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
    @ExceptionHandler(FamilyAccountNotFoundException.class)
    public ResponseEntity<String> handleFamilyAccountNotFoundException(FamilyAccountNotFoundException ex) {
        return ResponseEntity.badRequest().body("FamilyAccount not found: " + ex.getMessage());
    }
    
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleEmailNotFoundException(EmailNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
    @ExceptionHandler(OTPNotFoundException.class)
    public ResponseEntity<String> handleOTPNotFoundException(OTPNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    
    @ExceptionHandler(OTPValidationFailedException.class)
    public ResponseEntity<String> handleOTPValidationFailedException(OTPValidationFailedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
    }
}
