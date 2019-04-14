package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.service.SupportAmountService;
import com.jeanvar.housingfinance.service.SupportAmountYearlySummary;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    void summary() throws Exception {
        SupportAmountYearlySummary summary = new SupportAmountYearlySummary();

        Map<Year, Integer> yearSum = new HashMap<>();
        yearSum.put(Year.of(2004), 2);

        summary.setYearlySum(yearSum);

        val i1 = new Institute();
        i1.setName("a");

        val i2 = new Institute();
        i2.setName("b");

        Map<Institute, Integer> instSum = new HashMap<>();
        instSum.put(i1, 1);
        instSum.put(i2, 1);

        Map<Year, Map<Institute, Integer>> yearlySumPerInst = new HashMap<>();
        yearlySumPerInst.put(Year.of(2004), instSum);

        summary.setYearlySumPerInstitute(yearlySumPerInst);

        when(supportAmountService.getYearlySummary()).thenReturn(summary);

        this.mockMvc
            .perform(get("/api/v1/supportamount/summary"))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("$.name").value("주택금융 공급현황")
            )
            .andExpect(
                jsonPath("$.support_amount[0].year").value("2004년")
            )
            .andExpect(
                jsonPath("$.support_amount[0].total_amount").value(2)
            )
            .andExpect(
                jsonPath("$.support_amount[0].detail_amount.a").value(1)
            );
    }
}