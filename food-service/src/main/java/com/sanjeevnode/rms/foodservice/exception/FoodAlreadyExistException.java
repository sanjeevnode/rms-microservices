package com.sanjeevnode.rms.foodservice.exception;

public class FoodAlreadyExistException extends RuntimeException {
    public FoodAlreadyExistException(String message) {
        super(message);
    }
}
