package com.jeanvar.housingfinance.util;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SupportAmountCSVReader {
    private int numOfInstitutes;
    private List<String> instituteNames;
    private CSVReader csvReader;

    private SupportAmountCSVReader() {}

    public static SupportAmountCSVReader from(URI csvURI) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(new File(csvURI)));
            String[] header = csvReader.readNext();

            List<String> instituteNames = new ArrayList<>();
            // The first column is year and the second column is month
            for (int i = 2; i < header.length; i++) {
                if (header[i] != null && !header[i].isEmpty()) {
                    instituteNames.addAll(extractInstituteNames(header[i]));
                }
            }

            SupportAmountCSVReader supportAmountCSVReader = new SupportAmountCSVReader();
            supportAmountCSVReader.csvReader = csvReader;
            supportAmountCSVReader.numOfInstitutes = instituteNames.size();
            supportAmountCSVReader.instituteNames = instituteNames;

            return supportAmountCSVReader;
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    private static List<String> extractInstituteNames(String s) {
        s = s.replace("(억원)", "").trim();
        String[] tokens = s.split("/");

        return Arrays.stream(tokens)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public int getNumOfInstitutes() {
        return this.numOfInstitutes;
    }

    public List<String> getInstituteNames() {
        return this.instituteNames;
    }
}
