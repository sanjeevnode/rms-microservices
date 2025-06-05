package com.sanjeevnode.rms.orderservice.exception;

public class OrderAlreadyExistException extends RuntimeException {
    public OrderAlreadyExistException(String message) {
        super(message);
    }
}
