package com.jeanvar.housingfinance.util;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class SupportAmountCSVReaderTest {
    @Test
    void from() throws URISyntaxException {
        URI testCSV = getClass().getClassLoader().getResource("test.csv").toURI();
        SupportAmountCSVReader reader = SupportAmountCSVReader.from(testCSV);

        assertThat(reader.getNumOfInstitutes()).isEqualTo(3);
    }
}