package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.component.SupportAmountCSVReader;
import com.jeanvar.housingfinance.component.SupportAmountCSVReaderFactory;
import com.jeanvar.housingfinance.core.SupportAmount;
import com.jeanvar.housingfinance.repository.SupportAmountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}
