package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.core.Institute;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.Map;

@Getter
@Setter
public class SupportAmountYearlySummary {
    private Map<Year, Integer> yearlySum;
    private Map<Year, Map<Institute, Integer>> yearlySumPerInstitute;
}
