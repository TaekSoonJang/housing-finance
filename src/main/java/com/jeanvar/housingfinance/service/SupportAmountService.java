package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.component.SupportAmountCSVReader;
import com.jeanvar.housingfinance.component.SupportAmountCSVReaderFactory;
import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.core.SupportAmount;
import com.jeanvar.housingfinance.repository.SupportAmountRepository;
import com.jeanvar.housingfinance.repository.YearAndInstitute;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SupportAmountService {
    private SupportAmountCSVReaderFactory supportAmountCSVReaderFactory;
    private SupportAmountRepository supportAmountRepository;

    @Transactional
    public InsertSupportAmountInfo insertSupportAmountFromCSV(String pathToCSV) {
        SupportAmountCSVReader reader = supportAmountCSVReaderFactory.create(pathToCSV);

        List<SupportAmount> rows = reader.read();

        rows = supportAmountRepository.saveAll(rows);

        InsertSupportAmountInfo info = new InsertSupportAmountInfo();
        info.setInsertedRows(rows.size());
        info.setInstitutes(reader.getInstituteNames());

        return info;
    }

    public SupportAmountYearlySummary getYearlySummary() {
        Map<Year, Map<Institute, Integer>> yearlySumPerInstitute = supportAmountRepository.yearlySummary();

        SupportAmountYearlySummary summary = new SupportAmountYearlySummary();
        summary.setYearlySumPerInstitute(yearlySumPerInstitute);

        Map<Year, Integer> yearlySum = yearlySumPerInstitute.entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().values().stream().mapToInt(i -> i).sum()
            ));

        summary.setYearlySum(yearlySum);

        return summary;
    }

    public YearAndInstitute getHighestAmountOfYearAndInstitute() {
        return supportAmountRepository.highestAmountYearAndInstitute();
    }
}
