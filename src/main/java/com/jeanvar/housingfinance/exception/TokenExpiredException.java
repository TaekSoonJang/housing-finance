package com.jeanvar.housingfinance.exception;

public class TokenExpiredException extends IllegalArgumentException {
    public TokenExpiredException(String token) {
        super(token + " is expired.");
    }
}
