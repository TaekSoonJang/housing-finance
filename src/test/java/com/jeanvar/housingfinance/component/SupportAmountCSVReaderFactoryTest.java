package com.jeanvar.housingfinance.component;

import com.jeanvar.housingfinance.repository.InstituteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SupportAmountCSVReaderFactoryTest {
    @InjectMocks
    SupportAmountCSVReaderFactory supportAmountCSVReaderFactory;

    @Mock
    InstituteRepository instituteRepository;

    @Test
    void create() {
        String path = getClass().getClassLoader().getResource("test.csv").getPath();

        SupportAmountCSVReader reader = supportAmountCSVReaderFactory.create(path);

        assertThat(reader.getInstituteRepository()).isEqualTo(instituteRepository);
    }
}