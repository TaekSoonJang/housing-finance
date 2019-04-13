package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.controller.response.InstituteResponse;
import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.service.InstituteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/institute")
public class InstituteController {
    private InstituteService instituteService;

    @RequestMapping(
        value = "",
        method = RequestMethod.GET
    )
    List<InstituteResponse> getInstituteList() {
        List<Institute> instituteList = instituteService.getAllInstitutes();

        return instituteList.stream()
            .map(InstituteResponse::new)
            .collect(Collectors.toList());
    }
}
