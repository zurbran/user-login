package com.usermanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<ApiException> handleInvalidTokenException(InvalidTokenException e) {
        ApiException apiException = new ApiException();
        ApiError apiError = new ApiError();
        apiError.setDetail(e.getMessage());
        apiError.setCodigo(HttpStatus.UNAUTHORIZED.value());
        apiError.setTimestamp(Timestamp.from(Instant.now()));
        apiException.setError(Collections.singletonList(apiError));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiException);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiException> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ApiException apiException = new ApiException();
        ApiError apiError = new ApiError();
        apiError.setDetail(e.getMessage());
        apiError.setCodigo(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(Timestamp.from(Instant.now()));
        apiException.setError(Collections.singletonList(apiError));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiException);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiException> handleUserNotFoundException(UserNotFoundException e) {
        ApiException apiException = new ApiException();
        ApiError apiError = new ApiError();
        apiError.setDetail(e.getMessage());
        apiError.setCodigo(HttpStatus.BAD_REQUEST.value());
        apiError.setTimestamp(Timestamp.from(Instant.now()));
        apiException.setError(Collections.singletonList(apiError));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiException apiException = new ApiException();
        Timestamp timestamp = Timestamp.from(Instant.now());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ApiError> errors = fieldErrors.stream().map(fieldError -> {
            ApiError apiError = new ApiError();
            apiError.setTimestamp(timestamp);
            apiError.setCodigo(HttpStatus.BAD_REQUEST.value());
            apiError.setDetail(String.format("Wrong field: %s. Message: %s", fieldError.getField(), fieldError.getDefaultMessage()));
            return apiError;
        }).collect(Collectors.toList());
        apiException.setError(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiException);
    }

}
