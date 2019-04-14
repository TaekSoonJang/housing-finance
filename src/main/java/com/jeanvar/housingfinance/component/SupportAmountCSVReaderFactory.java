package com.jeanvar.housingfinance.component;

import com.jeanvar.housingfinance.repository.InstituteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@AllArgsConstructor
public class SupportAmountCSVReaderFactory {
    private InstituteRepository instituteRepository;

    public SupportAmountCSVReader create(String pathToCSV) {
        try {
            URI uri = new URI(pathToCSV);

            SupportAmountCSVReader reader = SupportAmountCSVReader.from(uri);
            reader.setInstituteRepository(instituteRepository);

            return reader;
        } catch (URISyntaxException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}
