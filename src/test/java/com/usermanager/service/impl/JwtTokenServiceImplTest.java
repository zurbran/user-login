package com.usermanager.service.impl;

import com.google.common.cache.Cache;
import com.usermanager.exception.ExpiredTokenException;
import com.usermanager.exception.InvalidTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceImplTest {

    @Mock
    Cache<String, Integer> tokenCache;
    @InjectMocks
    JwtTokenServiceImpl jwtTokenService = new JwtTokenServiceImpl("secret");
    @Captor
    ArgumentCaptor<String> tokenIdCaptor;

    @Test
    void generateJwtToken_success() {
        String token = jwtTokenService.generateJwtToken("test@email.com");

        verify(tokenCache, times(1)).put(tokenIdCaptor.capture(), eq(0));

        assertNotNull(token);
        assertTrue(Pattern.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", tokenIdCaptor.getValue()));
    }

    @Test
    void decodeJwtToken_success() {
        String expectedJwtId = "3ce208e7-68e3-45e1-8a05-58ff13ffbb5f";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJzdWIiOiJicnVzc2VsbEBicnVzc2VsbC5jb20iLCJyZWZyZXNoQ291bnQiOjAsImlzcyI6IkJDSS1DaGFsbGVuZ2UiLCJleHAiO" +
                "jE2NjEyNzQ1MTMsIm1heENvdW50IjoxLCJqdGkiOiIzY2UyMDhlNy02OGUzLTQ1ZTEtOGEwNS01OGZmMTNmZmJiNWYifQ" +
                ".GfTs0kCSpZsKMQyPsYiMKfRu1SsrCr1VGvpfcDZbEo4";

        when(tokenCache.getIfPresent(expectedJwtId)).thenReturn(0);

        jwtTokenService.decodeJwtToken(token);

        verify(tokenCache, times(1)).getIfPresent(tokenIdCaptor.capture());

        assertEquals(tokenIdCaptor.getValue(), expectedJwtId);
    }

    @Test
    void decodeJwtToken_invalid_token_exception_thrown() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJzdWIiOiJicnVzc2VsbEBicnVzc2VsbC5jb20iLCJyZWZyZXNoQ291bnQiOjAsImlzcyI6IkJDSS1DaGFsbGVuZ2UiLCJleHAiO" +
                "jE2NjEyNzQ1MTMsIm1heENvdW5invalidLCJqdGkiOiIzY2UyMDhlNy02OGUzLTQ1ZTEtOGEwNS01OGZmMTNmZmJiNWYifQ" +
                ".GfTs0kCSpZsKMQyPsYiMKfRu1SsrCr1VGvpfcDZbEo4";

        assertThrows(InvalidTokenException.class, () -> jwtTokenService.decodeJwtToken(token));
    }

    @Test
    void decodeJwtToken_expired_token_exception_thrown() {
        String expectedJwtId = "3ce208e7-68e3-45e1-8a05-58ff13ffbb5f";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJzdWIiOiJicnVzc2VsbEBicnVzc2VsbC5jb20iLCJyZWZyZXNoQ291bnQiOjAsImlzcyI6IkJDSS1DaGFsbGVuZ2UiLCJleHAiO" +
                "jE2NjEyNzQ1MTMsIm1heENvdW50IjoxLCJqdGkiOiIzY2UyMDhlNy02OGUzLTQ1ZTEtOGEwNS01OGZmMTNmZmJiNWYifQ" +
                ".GfTs0kCSpZsKMQyPsYiMKfRu1SsrCr1VGvpfcDZbEo4";

        when(tokenCache.getIfPresent(expectedJwtId)).thenReturn(1);

        assertThrows(ExpiredTokenException.class, () -> jwtTokenService.decodeJwtToken(token));
    }

    @Test
    void decodeJwtToken_cache_miss_exception_thrown() {
        String expectedJwtId = "3ce208e7-68e3-45e1-8a05-58ff13ffbb5f";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJzdWIiOiJicnVzc2VsbEBicnVzc2VsbC5jb20iLCJyZWZyZXNoQ291bnQiOjAsImlzcyI6IkJDSS1DaGFsbGVuZ2UiLCJleHAiO" +
                "jE2NjEyNzQ1MTMsIm1heENvdW50IjoxLCJqdGkiOiIzY2UyMDhlNy02OGUzLTQ1ZTEtOGEwNS01OGZmMTNmZmJiNWYifQ" +
                ".GfTs0kCSpZsKMQyPsYiMKfRu1SsrCr1VGvpfcDZbEo4";

        when(tokenCache.getIfPresent(expectedJwtId)).thenReturn(null);

        assertThrows(InvalidTokenException.class, () -> jwtTokenService.decodeJwtToken(token));
    }
}