package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.SupportAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupportAmountRepository extends JpaRepository<SupportAmount, Long>, SupportAmountRepositoryCustom {
}
