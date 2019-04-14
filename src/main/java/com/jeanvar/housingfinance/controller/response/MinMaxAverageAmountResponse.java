package com.jeanvar.housingfinance.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeanvar.housingfinance.repository.YearAndAmount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MinMaxAverageAmountResponse {
    private String bank;

    @JsonProperty("support_amount")
    private List<YearAndAmount> supportAmount;
}
