package com.usermanager.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String token) {
        super(String.format("No users were found with token: %s", token));
    }
}
