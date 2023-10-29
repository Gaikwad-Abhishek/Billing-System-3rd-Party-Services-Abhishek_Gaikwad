package com.collectionservice.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.collectionservice.exception.NoCollectionRecordFound;
import com.collectionservice.exception.SuspendedAccountNotFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(NoCollectionRecordFound.class)
    public ResponseEntity<String> handleNoCollectionRecordFound(NoCollectionRecordFound ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
    @ExceptionHandler(SuspendedAccountNotFoundException.class)
    public ResponseEntity<String> handleSuspendedAccountNotFoundException(SuspendedAccountNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
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
