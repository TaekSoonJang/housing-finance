package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.exception.InvalidTokenException;
import com.jeanvar.housingfinance.exception.TokenExpiredException;
import com.jeanvar.housingfinance.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthServiceTest {
    @InjectMocks
    JWTAuthService authService;

    @Mock
    SecurityProperties securityProperties;

    SecretKey secretKey;

    @BeforeEach
    void setUp() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Test
    void createToken() {
        when(securityProperties.getSecret()).thenReturn(secretKey);
        String jwt = authService.createToken("userId");

        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();

        assertThat(body.getSubject()).isEqualTo("userId");
        assertThat(body.getExpiration()).isAfter(Date.from(Instant.now().plus(23, ChronoUnit.HOURS)));
        assertThat(body.getExpiration()).isBefore(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)));
    }

    @Test
    void refreshToken() {
        authService = spy(authService);

        Claims claims = mock(Claims.class);

        when(claims.getSubject()).thenReturn("userId");
        when(claims.getExpiration()).thenReturn(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)));
        doReturn(claims).when(authService).getClaimsFromJWS("token");
        doReturn("newToken").when(authService).createToken("userId");

        String newToken = authService.refreshToken("token");

        assertThat(newToken).isEqualTo("newToken");
    }

    @Test
    void refreshToken_expired() {
        authService = spy(authService);

        Claims claims = mock(Claims.class);
        doReturn(claims).when(authService).getClaimsFromJWS("token");
        when(claims.getExpiration()).thenReturn(Date.from(Instant.now().minus(1, ChronoUnit.HOURS)));

        assertThatExceptionOfType(TokenExpiredException.class).isThrownBy(() -> authService.refreshToken("token"));
    }

    @Test
    void getClaimsFromJWS() {
        when(securityProperties.getSecret()).thenReturn(secretKey);

        Claims claims = authService.getClaimsFromJWS(authService.createToken("userId"));

        assertThat(claims.getSubject()).isEqualTo("userId");
    }

    @Test
    void isValidToken() {
        authService = spy(authService);

        Claims claims = mock(Claims.class);
        doReturn(claims).when(authService).getClaimsFromJWS("token");

        Date expired = Date.from(Instant.now().minus(1, ChronoUnit.HOURS));
        Date validTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        when(claims.getExpiration())
            .thenReturn(expired)
            .thenReturn(validTime);

        assertThat(authService.isValidToken("token")).isFalse();
        assertThat(authService.isValidToken("token")).isTrue();
    }
}