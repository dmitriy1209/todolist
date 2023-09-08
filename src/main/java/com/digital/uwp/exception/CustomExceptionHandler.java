package com.digital.uwp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity validationException(IllegalArgumentException exception, WebRequest request) {
        log.error("ValidationException : ", exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity apiException(ApiException exception, WebRequest request) {
        log.error("ApiException : ", exception);
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getMessage());
    }
}
