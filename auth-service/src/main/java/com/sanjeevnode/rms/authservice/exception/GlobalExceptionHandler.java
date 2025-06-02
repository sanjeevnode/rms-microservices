package com.sanjeevnode.rms.authservice.exception;

import com.sanjeevnode.rms.authservice.utils.ApiResponse;
import com.sanjeevnode.rms.authservice.utils.AppLogger;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final AppLogger log = new AppLogger(GlobalExceptionHandler.class, "GlobalExceptionHandler");

    private ResponseEntity<ApiResponse> buildErrorResponse(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status).body(ApiResponse.builder().status(status).message(message).data(data).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        log.warn("Validation failed: %s", errors.toString());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation error", errors);
    }


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        log.warn(ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, "User already exists " + ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error: %s", ex, ex.getMessage());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Illegal argument: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid argument : " + ex.getMessage(), null);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse> handleInvalidTokenException(InvalidTokenException ex) {
        log.warn("Invalid token: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid token", null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn(ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "User not found", null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.warn("Method not supported: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed: " + ex.getMessage(), null);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        log.warn("JWT token expired: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "JWT token expired", null);
    }

}
