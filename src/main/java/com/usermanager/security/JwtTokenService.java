package com.usermanager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.usermanager.exception.ExpiredTokenException;
import com.usermanager.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class JwtTokenService {
    public static final int VALID_TOKEN_MINUTES = 60;
    public static final int MAX_TOKEN_REFRESH = 1;
    public Cache<String, Integer> tokenCache =
            CacheBuilder.newBuilder()
                        .expireAfterWrite(VALID_TOKEN_MINUTES, TimeUnit.MINUTES)
                        .build();
    @Value("${security.secret}")
    private String secret;

    public String generateJwtToken(String email) {
        String jwtId = UUID.randomUUID().toString();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        tokenCache.put(jwtId, 0);
        return JWT.create()
                .withIssuer("BCI-Challenge")
                .withSubject(email)
                .withClaim("refreshCount", 0)
                .withClaim("maxCount", 1)
                .withJWTId(jwtId)
                .withExpiresAt(Instant.now().plus(VALID_TOKEN_MINUTES, ChronoUnit.MINUTES))
                .sign(algorithm);
    }

    public String decodeJwtToken(String accessToken) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
            String jwtId = decodedJWT.getClaim("jti").asString();
            Integer tokenCount = tokenCache.getIfPresent(jwtId);
            if (tokenCount == null) {
                throw new InvalidTokenException(accessToken);
            }
            if (tokenCount >= MAX_TOKEN_REFRESH) {
                throw new ExpiredTokenException(accessToken);
            }
            tokenCache.put(jwtId, tokenCount + 1);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(accessToken);
        }
    }

}
