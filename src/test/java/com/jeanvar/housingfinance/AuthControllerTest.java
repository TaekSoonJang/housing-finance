package com.jeanvar.housingfinance;

import com.jeanvar.housingfinance.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @Test
    void refreshToken() throws Exception {
        when(authService.refreshToken("token")).thenReturn("newToken");

        mockMvc.perform(
            put("/api/v1/auth/token")
                .param("op", "refresh")
                .header("Authorization", "Bearer token")
        ).andExpect(
            status().isOk()
        ).andExpect(
            jsonPath("$.token").value("newToken")
        );
    }
}