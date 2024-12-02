package com.supermarketcheckout.exception;

public class InvalidCartException extends RuntimeException {

    public InvalidCartException() {
        super();
    }

    public InvalidCartException(String message) {
        super(message);
    }
}

