package com.karold.swreportapp.exception;

public class JsonToObjectMappingException extends RuntimeException {

    public JsonToObjectMappingException(String json, Class<?> targetType, Exception e) {
        super("Can't covert json: " + json + " to target type: " + targetType, e);
    }
}
