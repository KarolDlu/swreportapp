package com.karold.swreportapp.exception;

public class ResultsCountNotEqualsOneException extends RuntimeException {

    public ResultsCountNotEqualsOneException() {
        super("Can't get single result cause count not equals one.");
    }
}
