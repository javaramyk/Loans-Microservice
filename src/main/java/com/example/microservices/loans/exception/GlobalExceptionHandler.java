package com.example.microservices.loans.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	protected ResponseEntity<Object> handleMethodArgumentNotValidException(Exception exception,)
}
