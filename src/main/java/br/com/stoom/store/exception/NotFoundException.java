package br.com.stoom.store.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, Exception e) {
        super(message);
    }

    public NotFoundException(String message) {

    }
}

