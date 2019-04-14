package com.jeanvar.housingfinance.interceptor;

import com.jeanvar.housingfinance.exception.InvalidTokenException;
import com.jeanvar.housingfinance.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthInterceptorImpl implements AuthInterceptor {
    private AuthService authService;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        String authHeader = request.getHeader("Authorization");

        String token = Optional.ofNullable(authHeader)
            .map(i -> i.replace("Bearer", "").trim())
            .orElseThrow(() -> new InvalidTokenException(authHeader));

        return authService.isValidToken(token);
    }
}
