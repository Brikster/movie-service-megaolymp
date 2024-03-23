package ru.ilyaand.movieservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ilyaand.movieservice.dto.ApiError;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<?> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                String.join("\n", errors));
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler({ CustomValidationException.class })
    public ResponseEntity<?> handleCustomValidationException(
            CustomValidationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                ex.getMessage());
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<?> handleServerErrors(
            Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage());
        return ResponseEntity.status(500).body(apiError);
    }

}
