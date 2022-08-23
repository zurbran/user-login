package com.usermanager.controller.dto;

import lombok.Data;

@Data
public class UserPhoneDto {
    private Long number;
    private int cityCode;
    private int countryCode;
}
