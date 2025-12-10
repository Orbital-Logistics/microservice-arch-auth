package org.orbitalLogistic.spacecraft.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SpacecraftNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSpacecraftNotFoundException(SpacecraftNotFoundException ex) {
        log.warn("Spacecraft not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(SpacecraftAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSpacecraftAlreadyExistsException(SpacecraftAlreadyExistsException ex) {
        log.warn("Spacecraft already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Conflict",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(SpacecraftTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSpacecraftTypeNotFoundException(SpacecraftTypeNotFoundException ex) {
        log.warn("Spacecraft type not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
        log.warn("Data not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            errors.put(field, error.getDefaultMessage());
        });

        return ResponseEntity.badRequest()
                .body(new ValidationErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Validation failed",
                        errors
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUnhandledExceptions(Exception ex) {
        log.error("Unexpected internal error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        "An unexpected error occurred"
                ));
    }

    public record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message
    ) {}

    public record ValidationErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            Map<String, String> details
    ) {}
}
