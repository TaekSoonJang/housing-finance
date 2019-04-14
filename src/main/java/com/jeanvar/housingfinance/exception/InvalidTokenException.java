package com.jeanvar.housingfinance.exception;

public class InvalidTokenException extends IllegalArgumentException {
    public InvalidTokenException(String token) {
        super(token + " is not valid.");
    }
}
