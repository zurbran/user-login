package com.usermanager.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String token) {
        super(String.format("The token: %s has expired", token));
    }
}
