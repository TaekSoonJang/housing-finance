package com.jeanvar.housingfinance.exception;

public class WrongUserException extends IllegalArgumentException {
    public WrongUserException(String s) {
        super(s);
    }
}
