package com.jeanvar.housingfinance.repository;

import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public class YearAndInstitute {
    private Year year;
    private String instituteName;
}
