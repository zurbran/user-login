package com.usermanager.service.impl;

import com.usermanager.controller.dto.LoginDto;
import com.usermanager.controller.dto.UserSignUpDto;
import com.usermanager.controller.dto.response.UserLoginSuccessResponse;
import com.usermanager.controller.dto.response.UserSuccessSignUpResponse;
import com.usermanager.domain.User;
import com.usermanager.exception.UserAlreadyExistsException;
import com.usermanager.exception.UserNotFoundException;
import com.usermanager.mapper.UserLoginSuccessMapper;
import com.usermanager.mapper.UserSuccessSignUpMapper;
import com.usermanager.repository.UserPhoneRepository;
import com.usermanager.repository.UserRepository;
import com.usermanager.security.JwtTokenService;
import com.usermanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final UserPhoneRepository userPhoneRepository;

    @Override
    public UserSuccessSignUpResponse signUp(UserSignUpDto dto) {
        String userEmail = dto.getEmail();
        userRepository.findByEmail(userEmail).ifPresent(usr -> {
            throw new UserAlreadyExistsException(usr.getEmail());
        });
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(userEmail);
        user.setCreated(OffsetDateTime.now());
        user.setPassword(dto.getPassword());
        user.setIsActive(true);
        userRepository.save(user);
        dto.getPhones().forEach(phone -> phone.setUser(user));
        userPhoneRepository.saveAll(dto.getPhones());
        String accessToken = jwtTokenService.generateJwtToken(userEmail);
        return UserSuccessSignUpMapper.UserToUserSuccessSignUpResponse(user, accessToken);
    }

    @Override
    public UserLoginSuccessResponse login(LoginDto dto) {
        String accessToken = dto.getAccessToken();
        String email = jwtTokenService.decodeJwtToken(accessToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(dto.getAccessToken()));
        user.setLastLogin(OffsetDateTime.now());
        userRepository.save(user);
        accessToken = jwtTokenService.generateJwtToken(email);
        return UserLoginSuccessMapper.UserToUserLoginSuccessResponse(user, accessToken);
    }
}
