package com.sanjeevnode.rms.orderservice.exception;

import com.sanjeevnode.rms.orderservice.utils.ApiResponse;
import com.sanjeevnode.rms.orderservice.utils.AppLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
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
        return ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .status(status)
                        .message(message)
                        .data(data)
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        log.warn("Validation failed: %s", errors.toString());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation error", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error: %s", ex, ex.getMessage());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Illegal argument: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid argument : "+ex.getMessage(), null);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse> handleInvalidTokenException(InvalidTokenException ex) {
        log.warn("Invalid token: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid token", null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.warn("Method not supported: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed: " + ex.getMessage(), null);
    }

    @ExceptionHandler(OrderAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleFoodAlreadyExistException(OrderAlreadyExistException ex) {
        log.warn("Food already exists: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, "Food already exists: " + ex.getMessage(), null);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFoodNotFoundException(OrderNotFoundException ex) {
        log.warn("Food not found: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Food not found: " + ex.getMessage(), null);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        log.warn("Authorization denied: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Access denied: " + ex.getMessage(), null);
    }
}
