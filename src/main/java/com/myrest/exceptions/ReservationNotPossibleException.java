package com.myrest.exceptions;

public class ReservationNotPossibleException extends RuntimeException {
    public ReservationNotPossibleException(String message) {
        super(message);
    }
}

