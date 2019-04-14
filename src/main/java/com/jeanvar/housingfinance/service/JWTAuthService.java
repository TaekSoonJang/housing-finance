package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@AllArgsConstructor
public class JWTAuthService implements AuthService {
    private SecurityProperties securityProperties;

    @Override
    public String createToken(String userId) {
        return Jwts.builder()
            .setSubject(userId)
            .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
            .signWith(securityProperties.getSecret())
            .compact();
    }

    @Override
    public String refreshToken(String token) {
        Claims claims = getClaimsFromJWS(token);

        return createToken(claims.getSubject());
    }

    public Claims getClaimsFromJWS(String token) {
        return Jwts.parser()
            .setSigningKey(securityProperties.getSecret())
            .parseClaimsJws(token)
            .getBody();
    }
}
