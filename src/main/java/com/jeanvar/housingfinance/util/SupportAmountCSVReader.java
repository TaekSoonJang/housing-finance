package com.jeanvar.housingfinance.util;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class SupportAmountCSVReader {
    private int numOfInstitutes;
    private CSVReader csvReader;

    private SupportAmountCSVReader() {}

    public static SupportAmountCSVReader from(URI csvURI) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(new File(csvURI)));
            String[] header = csvReader.readNext();

            int numOfInstitutes = 0;
            // The first column is year and the second column is month
            for (int i = 2; i < header.length; i++) {
                if (header[i] != null && !header[i].isEmpty()) {
                    numOfInstitutes += 1;
                }
            }

            SupportAmountCSVReader supportAmountCSVReader = new SupportAmountCSVReader();
            supportAmountCSVReader.csvReader = csvReader;
            supportAmountCSVReader.numOfInstitutes = numOfInstitutes;

            return supportAmountCSVReader;
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public int getNumOfInstitutes() {
        return this.numOfInstitutes;
    }
}
