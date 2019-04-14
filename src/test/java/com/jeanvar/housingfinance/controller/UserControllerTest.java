package com.jeanvar.housingfinance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeanvar.housingfinance.service.UserDTO;
import com.jeanvar.housingfinance.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void singup() throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("userId", "user");
        body.put("password", "password");

        mockMvc.perform(
            post("/api/v1/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
        ).andExpect(
            status().isOk()
        );

        verify(userService, times(1)).saveUser(any(UserDTO.class));
    }
}