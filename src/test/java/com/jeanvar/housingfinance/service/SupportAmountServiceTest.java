package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.component.SupportAmountCSVReader;
import com.jeanvar.housingfinance.component.SupportAmountCSVReaderFactory;
import com.jeanvar.housingfinance.core.SupportAmount;
import com.jeanvar.housingfinance.repository.SupportAmountRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

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

        supportAmountService.insertSupportAmountFromCSV(path);

        verify(supportAmountRepository, times(1)).saveAll(Arrays.asList(s1, s2));
    }
}