package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.component.SupportAmountCSVReader;
import com.jeanvar.housingfinance.component.SupportAmountCSVReaderFactory;
import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.core.SupportAmount;
import com.jeanvar.housingfinance.repository.SupportAmountRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportAmountServiceTest {
    @InjectMocks
    SupportAmountService supportAmountService;

    @Mock
    SupportAmountCSVReaderFactory supportAmountCSVReaderFactory;
    @Mock
    SupportAmountRepository supportAmountRepository;
    @Mock
    SupportAmountCSVReader supportAmountCSVReader;

    @Test
    void insertSupportAmountFromCSV() {
        String path = "path";
        when(supportAmountCSVReaderFactory.create(path)).thenReturn(supportAmountCSVReader);

        val s1 = new SupportAmount();
        val s2 = new SupportAmount();

        when(supportAmountCSVReader.read()).thenReturn(Arrays.asList(s1, s2));
        when(supportAmountCSVReader.getInstituteNames()).thenReturn(Arrays.asList("i1", "i2"));
        when(supportAmountRepository.saveAll(any())).thenAnswer(i -> i.getArgument(0));

        InsertSupportAmountInfo info = supportAmountService.insertSupportAmountFromCSV(path);

        verify(supportAmountRepository, times(1)).saveAll(Arrays.asList(s1, s2));

        assertThat(info.getInsertedRows()).isEqualTo(2);
        assertThat(info.getInstitutes()).containsExactly("i1", "i2");
    }

    @Test
    void yearlySummary() {
        val i1 = new Institute();
        val i2 = new Institute();

        Map<Institute, Integer> instSum = new HashMap<>();
        instSum.put(i1, 1);
        instSum.put(i2, 1);

        Map<Year, Map<Institute, Integer>> yearlySummaryData = new HashMap<>();

        yearlySummaryData.put(Year.of(2018), instSum);

        when(supportAmountRepository.yearlySummary()).thenReturn(yearlySummaryData);

        SupportAmountYearlySummary summary = supportAmountService.getYearlySummary();

        assertThat(summary.getYearlySum()).containsEntry(Year.of(2018), 2);
    }
}