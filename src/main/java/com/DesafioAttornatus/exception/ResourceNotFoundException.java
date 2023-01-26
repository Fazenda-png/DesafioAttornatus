package com.DesafioAttornatus.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id) {
        super("Resource Not found. Id " + id);
    }
}
