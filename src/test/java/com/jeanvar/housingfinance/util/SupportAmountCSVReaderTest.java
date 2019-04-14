package com.jeanvar.housingfinance.util;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.core.SupportAmount;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SupportAmountCSVReaderTest {
    URI testCSV;

    @BeforeEach
    void setUp() throws URISyntaxException {
        testCSV = getClass().getClassLoader().getResource("test.csv").toURI();
    }

    @Test
    void from() {
        SupportAmountCSVReader reader = SupportAmountCSVReader.from(testCSV);

        assertThat(reader.getNumOfInstitutes()).isEqualTo(4);
        assertThat(reader.getInstituteNames()).containsExactly("a은행", "b은행", "c은행", "d은행");
        assertThat(reader.getInstituteColumnMap())
                .containsEntry("a은행", 2)
                .containsEntry("b은행", 3)
                .containsEntry("c은행", 4)
                .containsEntry("d은행", 4);
    }

    @Test
    void read() {
        SupportAmountCSVReader reader = SupportAmountCSVReader.from(testCSV);

        val i1 = new Institute();
        i1.setCode("c1");
        i1.setName("c은행");

        val i2 = new Institute();
        i2.setCode("c2");
        i2.setName("d은행");

        List<Institute> institutes = Arrays.asList(i1, i2);
        List<SupportAmount> ret = reader.read(institutes);

        assertThat(ret).hasSize(4);
        assertThat(ret.get(0)).satisfies(s -> {
            assertThat(s.getInstitute()).isEqualTo(i1);
            assertThat(s.getYear()).isEqualTo(Year.of(1));
            assertThat(s.getMonth()).isEqualTo(Month.of(2));
            assertThat(s.getAmount()).isEqualTo(5);
        });
        assertThat(ret.get(1)).satisfies(s -> {
            assertThat(s.getInstitute()).isEqualTo(i2);
            assertThat(s.getYear()).isEqualTo(Year.of(1));
            assertThat(s.getMonth()).isEqualTo(Month.of(2));
            assertThat(s.getAmount()).isEqualTo(5);
        });
    }
}