package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.Institute;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InstituteRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    InstituteRepository instituteRepository;

    @Test
    void crud() {
        Institute institute = new Institute();
        institute.setCode("code");
        institute.setName("name");

        institute = instituteRepository.save(institute);

        entityManager.flush();
        entityManager.clear();

        assertThat(instituteRepository.findByInstituteCode("code"))
            .hasValueSatisfying(s -> assertThat(s.getName()).isEqualTo("name"));
    }
}