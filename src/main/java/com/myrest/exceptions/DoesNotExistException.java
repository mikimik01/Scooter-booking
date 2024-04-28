package com.myrest.exceptions;

public class DoesNotExistException extends RuntimeException {
    DoesNotExistException(String message){
        super(message);
    }
}
