package com.supermarketcheckout.exception;


public class InvalidCSVFormatException extends RuntimeException {

    public InvalidCSVFormatException() {
        super();
    }

    public InvalidCSVFormatException(String message) {
        super(message);
    }

}
