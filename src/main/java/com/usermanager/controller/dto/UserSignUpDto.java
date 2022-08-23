package com.usermanager.controller.dto;

import com.usermanager.domain.UserPhone;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class UserSignUpDto {
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
            message = "The email is invalid")
    @Column(unique = true)
    private String email;
    @Pattern(regexp = "(?=^(?:\\D*\\d\\D*){2}$)(?=^(?:[a-z0-9]*[A-Z][a-z0-9]*)$)^\\w{8,12}$",
            message = "Password must contain one uppercase letter, two digits and a length between 8 and 12 characters")
    private String password;
    private Set<UserPhone> phones;
}
