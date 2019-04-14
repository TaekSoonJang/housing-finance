package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    SecurityProperties securityProperties;

    @Test
    void createJWT() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        when(securityProperties.getSecret()).thenReturn(secretKey);

        String jwt = authService.createJWT("userId");

        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();

        assertThat(body.getSubject()).isEqualTo("userId");
        assertThat(body.getExpiration()).isAfter(Date.from(Instant.now().plus(23, ChronoUnit.HOURS)));
        assertThat(body.getExpiration()).isBefore(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)));
    }
}