package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.controller.response.MinMaxAverageAmountResponse;
import com.jeanvar.housingfinance.controller.response.SupportAmountSummaryResponse;
import com.jeanvar.housingfinance.controller.response.SupportAmountYearySummaryResponse;
import com.jeanvar.housingfinance.controller.response.TopAmountYearAndInstituteResponse;
import com.jeanvar.housingfinance.repository.HighLowSupportAmount;
import com.jeanvar.housingfinance.repository.YearAndInstitute;
import com.jeanvar.housingfinance.service.InsertSupportAmountInfo;
import com.jeanvar.housingfinance.service.SupportAmountService;
import com.jeanvar.housingfinance.service.SupportAmountYearlySummary;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/supportamount")
@AllArgsConstructor
public class SupportAmountController {
    private SupportAmountService supportAmountService;

    @RequestMapping(
        value = "",
        method = RequestMethod.POST,
        params = "csv=true"
    )
    public InsertSupportAmountInfo insertDataFromCSV(@RequestParam("file") MultipartFile file) throws IOException {
        File tmpFile = new File("/tmp/" + file.getOriginalFilename() + "." + Instant.now());
        file.transferTo(tmpFile);

        InsertSupportAmountInfo info = supportAmountService.insertSupportAmountFromCSV(tmpFile.getCanonicalPath());

        tmpFile.delete();

        return info;
    }

    @RequestMapping(
        value = "/summary",
        method = RequestMethod.GET
    )
    public SupportAmountSummaryResponse summary() {
        SupportAmountYearlySummary summary = supportAmountService.getYearlySummary();

        SupportAmountSummaryResponse res = new SupportAmountSummaryResponse();
        res.setName("주택금융 공급현황");

        List<SupportAmountYearySummaryResponse> yearlyRes = summary.years()
            .stream()
            .map(y -> {
                SupportAmountYearySummaryResponse yres = new SupportAmountYearySummaryResponse();
                yres.setYear(y.getValue() + "년");
                yres.setTotalAmount(summary.getYearlySum().get(y));

                Map<String, Integer> instSum = summary.getYearlySumPerInstitute()
                    .get(y)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> e.getValue()
                    ));

                yres.setDetailAmount(instSum);

                return yres;
            })
            .collect(Collectors.toList());

        res.setSupportAmount(yearlyRes);

        return res;
    }

    @RequestMapping(
        value = "/top-year-inst",
        method = RequestMethod.GET
    )
    public TopAmountYearAndInstituteResponse topAmountYearAndInstituteResponse() {
        YearAndInstitute yearAndInstitute = supportAmountService.getHighestAmountOfYearAndInstitute();

        TopAmountYearAndInstituteResponse res = new TopAmountYearAndInstituteResponse();
        res.setYear(yearAndInstitute.getYear().getValue());
        res.setBank(yearAndInstitute.getInstituteName());

        return res;
    }

    @RequestMapping(
        value = "/minmax-avg",
        method = RequestMethod.GET
    )
    public MinMaxAverageAmountResponse minMaxAverageAmountResponse(
        @RequestParam String instituteCode
    ) {
        HighLowSupportAmount highLowSupportAmount = supportAmountService.getHighLowSupportAmount(instituteCode);

        MinMaxAverageAmountResponse res = new MinMaxAverageAmountResponse();
        res.setBank(highLowSupportAmount.getInstitute().getName());

        res.setSupportAmount(Arrays.asList(
            highLowSupportAmount.getLow(),
            highLowSupportAmount.getHigh()
        ));

        return res;
    }
}
