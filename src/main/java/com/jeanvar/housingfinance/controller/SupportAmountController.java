package com.jeanvar.housingfinance.controller;

import com.jeanvar.housingfinance.service.InsertSupportAmountInfo;
import com.jeanvar.housingfinance.service.SupportAmountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

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
}