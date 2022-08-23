package com.usermanager.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String token) {
        super(String.format("The token: %s is invalid", token));
    }
}
