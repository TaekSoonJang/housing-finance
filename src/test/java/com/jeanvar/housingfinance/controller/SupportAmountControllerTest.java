package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.service.SupportAmountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SupportAmountController.class)
@ActiveProfiles("test")
class SupportAmountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SupportAmountService supportAmountService;

    @Test
    void insertDataFromCSV() throws Exception {
        MockMultipartFile file = Mockito.spy(new MockMultipartFile(
            "file",
            "test.csv",
            "text/csv",
            "a,b,c".getBytes()
        ));

        this.mockMvc
            .perform(
                multipart("/api/v1/supportamount?csv=true").file(file)
            )
            .andExpect(status().isOk());

        verify(supportAmountService, times(1)).insertSupportAmountFromCSV(anyString());
        verify(file, times(1)).transferTo(any(File.class));
    }
}