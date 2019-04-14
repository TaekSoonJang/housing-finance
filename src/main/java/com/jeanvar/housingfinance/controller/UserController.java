package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.controller.request.UserSignupRequest;
import com.jeanvar.housingfinance.service.UserDTO;
import com.jeanvar.housingfinance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;

    @RequestMapping(
        value = "/signup",
        method = RequestMethod.POST
    )
    public void signup(
        @RequestBody UserSignupRequest body
    ) {
        userService.saveUser(UserDTO.create(body.getUserId(), body.getPassword()));
    }
}
