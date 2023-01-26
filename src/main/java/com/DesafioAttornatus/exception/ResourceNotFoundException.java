package com.DesafioAttornatus.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id) {
        super("Pessoa com a identificação: " + id + " não foi encontrada");
    }
}
