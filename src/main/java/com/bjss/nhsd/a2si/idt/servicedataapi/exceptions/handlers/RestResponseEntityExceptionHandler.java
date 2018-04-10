package com.bjss.nhsd.a2si.idt.servicedataapi.exceptions.handlers;

import com.bjss.nhsd.a2si.idt.servicedataapi.exceptions.AuthenticationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Exception Handler is automatically registered by Spring Each annotated method will declare the exception
 * class it handles.
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // AuthenticationException is thrown if the username and password http headers don't match the
    // values in the configuration.
    // A http status of 403 (forbidden) is returned if the header values are wrong.
    // (If the header names themselves are wrong, a 400 (bad request) is automatically returned.
    @ExceptionHandler(value = { AuthenticationException.class })
    protected ResponseEntity<ExceptionResponse>
    handleAuthenticationException(AuthenticationException exception, WebRequest webRequest) {

        logger.debug("Handling AuthenticationException: {}", exception );

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now().format(dateTimeFormatter),
                exception.getMessage(),
                webRequest.getDescription(false));

        // Return Error Response and appropriate HTTP Status Code
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(value = { DuplicateKeyException.class} )
    protected ResponseEntity<ExceptionResponse>
    handleDuplicateKeyException(DuplicateKeyException exception, WebRequest webRequest) {

        logger.debug("Handling DuplicateKeyException: {}", exception );

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now().format(dateTimeFormatter),
                "Error thrown due to duplicate keys " +
                        "(this may only affect some of the records in the batch) - " +
                exception.getMessage(),
                webRequest.getDescription(false));

        // Return Error Response and appropriate HTTP Status Code
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // Generic, catch catch all handler
    @ExceptionHandler(value = { Throwable.class })
    protected ResponseEntity<ExceptionResponse>
    handleThrowable(Throwable exception, WebRequest webRequest) {

        logger.debug("Handling Throwable: {}", exception );

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now().format(dateTimeFormatter),
                exception.getMessage(),
                webRequest.getDescription(false));

        // Return Error Response and appropriate HTTP Status Code
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}