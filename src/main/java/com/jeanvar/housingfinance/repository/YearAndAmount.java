package com.jeanvar.housingfinance.repository;

import lombok.Data;

import java.time.Year;

@Data
public class YearAndAmount {
    private Year year;
    private int amount;
}
