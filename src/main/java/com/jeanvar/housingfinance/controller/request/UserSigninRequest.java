package com.jeanvar.housingfinance.controller.request;

import lombok.Getter;

@Getter
public class UserSigninRequest {
    private String userId;
    private String password;
}
