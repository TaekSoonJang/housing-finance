package com.jeanvar.housingfinance.controller.exception;

import com.jeanvar.housingfinance.exception.InvalidTokenException;
import com.jeanvar.housingfinance.exception.ReadCSVDataException;
import com.jeanvar.housingfinance.exception.TokenExpiredException;
import com.jeanvar.housingfinance.exception.WrongUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
        InvalidTokenException.class,
        TokenExpiredException.class,
        WrongUserException.class
    })
    protected ResponseEntity<Object> authenticationException(
        RuntimeException e,
        WebRequest request
    ) {
        return handleExceptionInternal(
            e,
            new ExceptionResponse(e.getMessage()),
            new HttpHeaders(),
            HttpStatus.FORBIDDEN,
            request
        );
    }

    @ExceptionHandler({
        ReadCSVDataException.class
    })
    protected ResponseEntity<Object> csvReadingException(
        RuntimeException e,
        WebRequest request
    ) {
        return handleExceptionInternal(
            e,
            new ExceptionResponse(e.getMessage()),
            new HttpHeaders(),
            HttpStatus.UNPROCESSABLE_ENTITY,
            request
        );
    }
}
