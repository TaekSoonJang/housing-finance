package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Optional<Institute> findByInstituteCode(String code);
}
