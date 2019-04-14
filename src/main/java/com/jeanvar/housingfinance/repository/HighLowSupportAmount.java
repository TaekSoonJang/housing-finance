package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.Institute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HighLowSupportAmount {
    private Institute institute;
    private YearAndAmount high;
    private YearAndAmount low;
}
