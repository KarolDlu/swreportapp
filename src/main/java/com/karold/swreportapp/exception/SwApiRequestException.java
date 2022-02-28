package com.karold.swreportapp.exception;

public class SwApiRequestException extends RuntimeException {

    public SwApiRequestException(String errorMessage, Exception e) {
        super("Error occurred while making request to SW API. Error message: " + errorMessage, e);
    }
}
