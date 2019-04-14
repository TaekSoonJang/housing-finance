package com.jeanvar.housingfinance.service;

import com.jeanvar.housingfinance.component.SupportAmountCSVReader;
import com.jeanvar.housingfinance.component.SupportAmountCSVReaderFactory;
import com.jeanvar.housingfinance.core.SupportAmount;
import com.jeanvar.housingfinance.repository.SupportAmountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupportAmountService {
    private SupportAmountCSVReaderFactory supportAmountCSVReaderFactory;
    private SupportAmountRepository supportAmountRepository;

    public void insertSupportAmountFromCSV(String pathToCSV) {
        SupportAmountCSVReader reader = supportAmountCSVReaderFactory.create(pathToCSV);

        List<SupportAmount> rows = reader.read();

        supportAmountRepository.saveAll(rows);
    }
}
