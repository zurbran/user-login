package com.usermanager.exception;

import lombok.Data;

import java.util.List;

@Data
public class ApiException {
    List<ApiError> error;
}
