package com.usermanager.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super(String.format("The user with email: %s already exists", email));
    }
}
