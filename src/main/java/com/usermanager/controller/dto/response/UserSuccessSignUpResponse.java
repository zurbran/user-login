package com.usermanager.controller.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserSuccessSignUpResponse {
    private String id;
    private String created;
    private String lastLogin;
    private String token;
    private Boolean isActive;
}
