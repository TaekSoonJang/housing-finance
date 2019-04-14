package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.exception.InvalidTokenException;
import com.jeanvar.housingfinance.exception.TokenExpiredException;
import com.jeanvar.housingfinance.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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

        if (isTokenExpired(claims)) {
            throw new TokenExpiredException(token);
        }

        return createToken(claims.getSubject());
    }

    @Override
    public boolean isValidToken(String token) {
        Claims claims = getClaimsFromJWS(token);
        // At this point, token is trustworthy.
        return !isTokenExpired(claims);
    }

    private boolean isTokenExpired(Claims claims) {
        if (claims.getExpiration().before(new Date())) {
            return true;
        } else {
            return false;
        }
    }

    Claims getClaimsFromJWS(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(securityProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException e) {
            throw new InvalidTokenException(token);
        }
    }
}
