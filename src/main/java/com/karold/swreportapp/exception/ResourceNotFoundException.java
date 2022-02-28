package com.karold.swreportapp.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceType, String name) {
        super(resourceType + " with given name: " + name + " not found.");
    }

    public ResourceNotFoundException(String resourceType, Long id) {
        super(resourceType + " with given " + id + " not found.");
    }
}
