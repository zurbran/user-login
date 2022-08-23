package com.usermanager.service;

public interface JwtTokenService {
    String generateJwtToken(String email);
    String decodeJwtToken(String accessToken);

}
