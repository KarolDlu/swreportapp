package com.karold.swreportapp.controller;

import com.karold.swreportapp.exception.ResourceNotFoundException;
import com.karold.swreportapp.exception.ResultsCountNotEqualsOneException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResultsCountNotEqualsOneException.class)
    public ResponseEntity<String> handleResultsCountNotEqualsOneException(ResultsCountNotEqualsOneException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
