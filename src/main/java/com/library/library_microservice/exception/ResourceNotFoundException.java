package com.library.library_microservice.exception;

public class ResourceNotFoundException extends RuntimeException {
   public ResourceNotFoundException(String message) {
        super(message);
    }
}
