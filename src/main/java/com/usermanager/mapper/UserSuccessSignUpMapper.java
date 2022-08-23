package com.usermanager.mapper;

import com.usermanager.controller.dto.response.UserSuccessSignUpResponse;
import com.usermanager.domain.User;

import java.time.format.DateTimeFormatter;

public final  class UserSuccessSignUpMapper {

    private UserSuccessSignUpMapper() {
    }

    public static UserSuccessSignUpResponse UserToUserSuccessSignUpResponse(User user, String token) {
        UserSuccessSignUpResponse response = new UserSuccessSignUpResponse();
        response.setId(user.getId());
        response.setToken(token);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd',' yyyy h:mm:ss a");
        response.setCreated(dtf.format(user.getCreated()));
        response.setIsActive(user.getIsActive());
        return response;
    }

}
