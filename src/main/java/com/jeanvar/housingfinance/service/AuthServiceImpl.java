package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.properties.SecurityProperties;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private SecurityProperties securityProperties;

    @Override
    public String createJWT(String userId) {
        return Jwts.builder()
            .setSubject(userId)
            .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
            .signWith(securityProperties.getSecret())
            .compact();
    }
}
