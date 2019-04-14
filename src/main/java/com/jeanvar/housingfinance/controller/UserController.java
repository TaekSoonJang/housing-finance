package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.controller.request.UserSigninRequest;
import com.jeanvar.housingfinance.controller.response.SigninResponse;
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
    public SigninResponse signup(
        @RequestBody UserSigninRequest body
    ) {
        UserDTO userDTO = userService.saveUser(UserDTO.create(body.getUserId(), body.getPassword()));

        SigninResponse res = new SigninResponse();
        res.setToken(userDTO.getJws());

        return res;
    }

    @RequestMapping(
        value = "/signin",
        method = RequestMethod.POST
    )
    public SigninResponse signin(
        @RequestBody UserSigninRequest body
    ) {
        UserDTO userDTO = UserDTO.create(body.getUserId(), body.getPassword());

        String token = userService.checkUserAndReturnJWS(userDTO);

        SigninResponse res = new SigninResponse();
        res.setToken(token);

        return res;
    }
}
