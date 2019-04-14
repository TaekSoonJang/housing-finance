package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.core.SupportAmount;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SupportAmountRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SupportAmountRepository supportAmountRepository;

    Institute i1;
    Institute i2;

    @BeforeEach
    void setUp() {
        Institute i1 = new Institute();
        i1.setCode("cc1");
        i1.setName("nn1");
        this.i1 = entityManager.persist(i1);

        Institute i2 = new Institute();
        i2.setCode("cc2");
        i2.setName("nn2");
        this.i2 = entityManager.persist(i2);
    }

    @Test
    void curd() {
        SupportAmount supportAmount = new SupportAmount();
        supportAmount.setYear(Year.of(2019));
        supportAmount.setMonth(Month.of(4));
        supportAmount.setInstitute(i1);
        supportAmount.setAmount(100);

        supportAmount = supportAmountRepository.save(supportAmount);

        entityManager.flush();
        entityManager.clear();

        assertThat(supportAmountRepository.findById(supportAmount.getId()))
            .hasValueSatisfying(s -> {
                assertThat(s.getYear()).isEqualByComparingTo(Year.of(2019));
                assertThat(s.getMonth()).isEqualByComparingTo(Month.APRIL);
                assertThat(s.getAmount()).isEqualTo(100);

                Institute institute = s.getInstitute();
                assertThat(institute.getCode()).isEqualTo("cc1");
                assertThat(institute.getName()).isEqualTo("nn1");
            });
    }

    @Test
    void yearlySummary() {
        val s1 = new SupportAmount();
        s1.setInstitute(i1);
        s1.setYear(Year.of(2018));
        s1.setMonth(Month.of(1));
        s1.setAmount(1);

        val s2 = new SupportAmount();
        s2.setInstitute(i1);
        s2.setYear(Year.of(2018));
        s2.setMonth(Month.of(2));
        s2.setAmount(1);

        val s3 = new SupportAmount();
        s3.setInstitute(i1);
        s3.setYear(Year.of(2018));
        s3.setMonth(Month.of(3));
        s3.setAmount(1);

        val s4 = new SupportAmount();
        s4.setInstitute(i2);
        s4.setYear(Year.of(2018));
        s4.setMonth(Month.of(1));
        s4.setAmount(1);

        val s5 = new SupportAmount();
        s5.setInstitute(i2);
        s5.setYear(Year.of(2018));
        s5.setMonth(Month.of(2));
        s5.setAmount(1);

        supportAmountRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));

        Map<Year, Map<Institute, Integer>> summary = supportAmountRepository.yearlySummary();

        assertThat(summary.get(Year.of(2018))).satisfies(instMap -> {
            assertThat(instMap).containsEntry(i1, 3);
            assertThat(instMap).containsEntry(i2, 2);
        });
    }

    @Test
    void highestYearAndInstitute() {
        val s1 = new SupportAmount();
        s1.setInstitute(i1);
        s1.setYear(Year.of(2018));
        s1.setMonth(Month.of(1));
        s1.setAmount(1);

        val s2 = new SupportAmount();
        s2.setInstitute(i1);
        s2.setYear(Year.of(2018));
        s2.setMonth(Month.of(2));
        s2.setAmount(1);

        val s3 = new SupportAmount();
        s3.setInstitute(i2);
        s3.setYear(Year.of(2017));
        s3.setMonth(Month.of(1));
        s3.setAmount(2);

        val s4 = new SupportAmount();
        s4.setInstitute(i2);
        s4.setYear(Year.of(2017));
        s4.setMonth(Month.of(2));
        s4.setAmount(2);

        supportAmountRepository.saveAll(Arrays.asList(s1, s2, s3, s4));

        YearAndInstitute yearAndInstitute = supportAmountRepository.highestAmountYearAndInstitute();

        assertThat(yearAndInstitute.getYear()).isEqualTo(Year.of(2017));
        assertThat(yearAndInstitute.getInstituteName()).isEqualTo("nn2");
    }
}