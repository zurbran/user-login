package com.usermanager.controller.dto.response;

import com.usermanager.controller.dto.UserPhoneDto;
import lombok.Data;

import java.util.Set;

@Data
public class UserLoginSuccessResponse {
    private String id;
    private String created;
    private String lastLogin;
    private String token;
    private Boolean isActive;
    private String name;
    private String email;
    private String password;
    private Set<UserPhoneDto> phones;
}
