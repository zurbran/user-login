package com.usermanager.service;

import com.usermanager.controller.dto.LoginDto;
import com.usermanager.controller.dto.UserSignUpDto;
import com.usermanager.controller.dto.response.UserLoginSuccessResponse;
import com.usermanager.controller.dto.response.UserSuccessSignUpResponse;
import com.usermanager.domain.User;
public interface UserService {
    UserSuccessSignUpResponse signUp(UserSignUpDto dto);
    UserLoginSuccessResponse login(LoginDto dto);
}
