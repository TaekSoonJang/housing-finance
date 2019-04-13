package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.repository.InstituteRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstituteServiceTest {
    @InjectMocks
    InstituteService instituteService;

    @Mock
    InstituteRepository instituteRepository;

    @Test
    void getAllInstitutes() {
        val i1 = new Institute();
        val i2 = new Institute();

        when(instituteRepository.findAll()).thenReturn(Arrays.asList(i1, i2));

        List<Institute> institutes = instituteService.getAllInstitutes();

        assertThat(institutes).containsExactly(i1, i2);
    }
}