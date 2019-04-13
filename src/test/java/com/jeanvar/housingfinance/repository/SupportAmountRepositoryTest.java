package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.core.SupportAmount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Month;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SupportAmountRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SupportAmountRepository supportAmountRepository;

    @Test
    void curd() {
        Institute i = new Institute();
        i.setCode("c");
        i.setName("n");

        i = entityManager.persist(i);

        SupportAmount supportAmount = new SupportAmount();
        supportAmount.setYear(Year.of(2019));
        supportAmount.setMonth(Month.of(4));
        supportAmount.setInstitute(i);
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
                assertThat(institute.getCode()).isEqualTo("c");
                assertThat(institute.getName()).isEqualTo("n");
            });
    }
}