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

        assertThat(reader.getNumOfInstitutes()).isEqualTo(4);
        assertThat(reader.getInstituteNames()).containsExactly("a은행", "b은행", "c은행", "d은행");
        assertThat(reader.getInstituteColumnMap())
                .containsEntry("a은행", 2)
                .containsEntry("b은행", 3)
                .containsEntry("c은행", 4)
                .containsEntry("d은행", 4);
    }
}