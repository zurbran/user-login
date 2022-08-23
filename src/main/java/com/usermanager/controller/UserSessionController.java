package com.usermanager.controller;

import com.usermanager.controller.dto.LoginDto;
import com.usermanager.controller.dto.UserSignUpDto;
import com.usermanager.controller.dto.response.UserLoginSuccessResponse;
import com.usermanager.controller.dto.response.UserSuccessSignUpResponse;
import com.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserSessionController {

    private final UserService userService;

    @Autowired
    public UserSessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSuccessSignUpResponse> signUp(@RequestBody @Valid UserSignUpDto dto) {
        return ResponseEntity.ok(userService.signUp(dto));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginSuccessResponse> login(@RequestBody LoginDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

}
