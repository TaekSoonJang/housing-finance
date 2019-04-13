package com.jeanvar.housingfinance.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeanvar.housingfinance.core.Institute;
import lombok.Getter;

@Getter
public class InstituteResponse {
    @JsonProperty("institute_code")
    private String instituteCode;

    @JsonProperty("institute_name")
    private String instituteName;

    public InstituteResponse(Institute institute) {
        this.instituteCode = institute.getCode();
        this.instituteName = institute.getName();
    }
}
