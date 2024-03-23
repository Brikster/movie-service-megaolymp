package ru.ilyaand.movieservice.exception;

public class CustomIntegrityException extends RuntimeException {

    public CustomIntegrityException(String message) {
        super(message);
    }

}
