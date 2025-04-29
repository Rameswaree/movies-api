package com.backbase.movies.exception;

/**
 * Custom exception class
 */
public class MovieServiceException extends Exception{

    public MovieServiceException(String message) {
        super(message);
    }
}
