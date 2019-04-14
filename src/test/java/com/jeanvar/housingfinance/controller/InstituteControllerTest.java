package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.service.InstituteService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstituteController.class)
@ActiveProfiles("test")
class InstituteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    InstituteService instituteService;

    @Test
    void getInstituteList() throws Exception {
        val i1 = new Institute();
        i1.setName("n1");
        i1.setCode("c1");

        val i2 = new Institute();
        i2.setName("i2");
        i2.setCode("c2");

        when(instituteService.getAllInstitutes()).thenReturn(Arrays.asList(i1, i2));

        this.mockMvc
            .perform(get("/api/v1/institute"))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$[0].institute_name").value("n1")
            )
            .andExpect(
                jsonPath("$[1].institute_code").value("c2")
            );
    }
}