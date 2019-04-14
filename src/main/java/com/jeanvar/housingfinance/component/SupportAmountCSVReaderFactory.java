package com.jeanvar.housingfinance.component;

import com.jeanvar.housingfinance.repository.InstituteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SupportAmountCSVReaderFactory {
    private InstituteRepository instituteRepository;

    public SupportAmountCSVReader create(String pathToCSV) {
        SupportAmountCSVReader reader = SupportAmountCSVReader.from(pathToCSV);
        reader.setInstituteRepository(instituteRepository);

        return reader;
    }
}
