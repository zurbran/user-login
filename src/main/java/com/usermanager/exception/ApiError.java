package com.usermanager.exception;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ApiError {
    private int codigo;
    private String detail;
    private Timestamp timestamp;
}
