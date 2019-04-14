package com.jeanvar.housingfinance.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SupportAmountYearySummaryResponse {
    private String year;

    @JsonProperty("total_amount")
    private int totalAmount;

    @JsonProperty("detail_amount")
    private Map<String, Integer> detailAmount;
}
