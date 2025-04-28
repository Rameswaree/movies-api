package com.backbase.movies.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionError handleConstraintViolationException(RuntimeException ex) {
        return ExceptionError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = MovieServiceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionError handleMovieServiceException(RuntimeException ex) {
        return ExceptionError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionError handleIllegalArgumentException(RuntimeException ex) {
        return ExceptionError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionError handleResourceNotFoundException(RuntimeException ex) {
        return ExceptionError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ExceptionError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getFieldError().getDefaultMessage())
                .build();
    }
}
