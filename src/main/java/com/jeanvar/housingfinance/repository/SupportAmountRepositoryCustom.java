package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.Institute;

import java.time.Year;
import java.util.Map;

public interface SupportAmountRepositoryCustom {
    Map<Year, Map<Institute, Integer>> yearlySummary();
}