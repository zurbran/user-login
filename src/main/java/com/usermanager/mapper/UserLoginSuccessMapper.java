package com.usermanager.mapper;

import com.usermanager.controller.dto.UserPhoneDto;
import com.usermanager.controller.dto.response.UserLoginSuccessResponse;
import com.usermanager.domain.User;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public final class UserLoginSuccessMapper {
    private UserLoginSuccessMapper(){}

    public static UserLoginSuccessResponse UserToUserLoginSuccessResponse(User user, String token) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd',' yyyy h:mm:ss a");
        UserLoginSuccessResponse response = new UserLoginSuccessResponse();
        response.setCreated(dtf.format(user.getCreated()));
        response.setName(user.getName());
        response.setPhones(user.getPhones().stream().map(userPhone -> {
            UserPhoneDto dto = new UserPhoneDto();
            dto.setNumber(userPhone.getNumber());
            dto.setCountryCode(userPhone.getCountryCode());
            dto.setCityCode(userPhone.getCityCode());
            return dto;
        }).collect(Collectors.toSet()));
        response.setLastLogin(dtf.format(user.getLastLogin()));
        response.setEmail(user.getEmail());
        response.setToken(token);
        response.setIsActive(user.getIsActive());
        response.setId(user.getId());
        response.setPassword(user.getPassword());
        return response;
    }

}
