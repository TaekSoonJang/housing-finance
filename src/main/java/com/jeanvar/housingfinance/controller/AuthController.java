package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.controller.response.SigninResponse;
import com.jeanvar.housingfinance.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @RequestMapping(
        value = "/token",
        method = RequestMethod.PUT,
        params = "op=refresh"
    )
    public SigninResponse refreshToken(
        @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer", "").trim();

        String newToken = authService.refreshToken(token);

        SigninResponse res = new SigninResponse();
        res.setToken(newToken);

        return res;
    }
}
