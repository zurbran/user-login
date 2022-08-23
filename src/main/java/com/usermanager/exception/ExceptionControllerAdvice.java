package com.usermanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ApiException> handleExpiredTokenException(ExpiredTokenException e) {
        ApiException apiException = new ApiException();
        ApiError apiError = new ApiError();
        apiError.setDetail(e.getMessage());
        apiError.setCodigo(HttpStatus.FORBIDDEN.value());
        apiError.setTimestamp(Timestamp.from(Instant.now()));
        apiException.setError(Collections.singletonList(apiError));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiException);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiException> handleExpiredTokenException(InvalidTokenException e) {
        ApiException apiException = new ApiException();
        ApiError apiError = new ApiError();
        apiError.setDetail(e.getMessage());
        apiError.setCodigo(HttpStatus.UNAUTHORIZED.value());
        apiError.setTimestamp(Timestamp.from(Instant.now()));
        apiException.setError(Collections.singletonList(apiError));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiException);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiException> handleExpiredTokenException(UserAlreadyExistsException e) {
        ApiException apiException = new ApiException();
        ApiError apiError = new ApiError();
        apiError.setDetail(e.getMessage());
        apiError.setCodigo(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(Timestamp.from(Instant.now()));
        apiException.setError(Collections.singletonList(apiError));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiException);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiException> handleExpiredTokenException(UserNotFoundException e) {
        ApiException apiException = new ApiException();
        ApiError apiError = new ApiError();
        apiError.setDetail(e.getMessage());
        apiError.setCodigo(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(Timestamp.from(Instant.now()));
        apiException.setError(Collections.singletonList(apiError));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiException);
    }
}
