package com.supermarketcheckout.exception;



public class InvalidPricingRulesException extends RuntimeException {

    public InvalidPricingRulesException() {
        super();
    }

    public InvalidPricingRulesException(String message) {
        super(message);
    }

}
