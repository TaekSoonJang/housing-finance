package com.jeanvar.housingfinance.service;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InsertSupportAmountInfo {
    private int insertedRows;
    private List<String> institutes;
}
