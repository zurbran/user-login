package com.usermanager.service.impl;

import com.usermanager.controller.dto.LoginDto;
import com.usermanager.controller.dto.UserSignUpDto;
import com.usermanager.controller.dto.response.UserLoginSuccessResponse;
import com.usermanager.controller.dto.response.UserSuccessSignUpResponse;
import com.usermanager.domain.User;
import com.usermanager.domain.UserPhone;
import com.usermanager.exception.UserAlreadyExistsException;
import com.usermanager.exception.UserNotFoundException;
import com.usermanager.repository.UserPhoneRepository;
import com.usermanager.repository.UserRepository;
import com.usermanager.service.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    JwtTokenService jwtTokenService;
    @Mock
    UserPhoneRepository userPhoneRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void signUp_no_phones_success() {
        UserSignUpDto dto = new UserSignUpDto();
        dto.setEmail("test@email.com");
        dto.setName("test");
        dto.setPassword("p4ssw0Rd");
        String token = "testToken";

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(jwtTokenService.generateJwtToken(dto.getEmail())).thenReturn(token);

        UserSuccessSignUpResponse response = userService.signUp(dto);

        verify(userRepository, times(1)).findByEmail(any());
        verify(userRepository, times(1)).save(any());
        verify(jwtTokenService, times(1)).generateJwtToken(any());
        assertEquals(response.getToken(), token);
    }

    @Test
    void signUp_with_phones_success() {
        UserSignUpDto dto = new UserSignUpDto();
        dto.setEmail("test@email.com");
        dto.setName("test");
        dto.setPassword("p4ssw0Rd");
        UserPhone phone = new UserPhone();
        phone.setCityCode(221);
        phone.setNumber(52423214L);
        phone.setCountryCode(54);
        Set<UserPhone> phones = new HashSet<>();
        phones.add(phone);
        dto.setPhones(phones);
        String token = "testToken";

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(jwtTokenService.generateJwtToken(dto.getEmail())).thenReturn(token);

        UserSuccessSignUpResponse response = userService.signUp(dto);

        verify(userRepository, times(1)).findByEmail(any());
        verify(userRepository, times(1)).save(any());
        verify(userPhoneRepository, times(1)).saveAll(any());
        verify(jwtTokenService, times(1)).generateJwtToken(any());
        assertEquals(response.getToken(), token);
    }

    @Test
    void signUp_duplicated_user_exception_thrown() {
        UserSignUpDto dto = new UserSignUpDto();
        dto.setEmail("test@email.com");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> userService.signUp(dto));
    }

    @Test
    void login_success() {
        LoginDto dto = new LoginDto();
        dto.setAccessToken("testToken");
        String email = "test@email.com";
        String newToken = "newTestToken";
        User user = new User();
        user.setCreated(OffsetDateTime.now());
        user.setPhones(new HashSet<>());

        when(jwtTokenService.decodeJwtToken(dto.getAccessToken())).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtTokenService.generateJwtToken(email)).thenReturn(newToken);

        UserLoginSuccessResponse response = userService.login(dto);

        assertEquals(response.getToken(), newToken);
        verify(userRepository, times(1)).findByEmail(any());
        verify(userRepository, times(1)).save(any());
        verify(jwtTokenService, times(1)).decodeJwtToken(any());
        verify(jwtTokenService, times(1)).generateJwtToken(any());
    }

    @Test
    void login_wrong_email_exception_thrown() {
        LoginDto dto = new LoginDto();
        dto.setAccessToken("testToken");
        String email = "test@email.com";

        when(jwtTokenService.decodeJwtToken(dto.getAccessToken())).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.login(dto));
    }
}