package com.jeanvar.housingfinance.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SupportAmountSummaryResponse {
    private String name;

    @JsonProperty("support_amount")
    private List<SupportAmountYearySummaryResponse> supportAmount;
}
