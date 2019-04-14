package com.jeanvar.housingfinance.service;

public interface AuthService {
    String createToken(String userId);
    String refreshToken(String token);
    boolean isValidToken(String token);
}
