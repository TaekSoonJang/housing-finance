package com.jeanvar.housingfinance.util;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.core.SupportAmount;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SupportAmountCSVReader {
    private Map<String, Integer> instituteColumnMap;
    private CSVReader csvReader;

    private SupportAmountCSVReader() {}

    public static SupportAmountCSVReader from(URI csvURI) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(new File(csvURI)));
            String[] header = csvReader.readNext();

            Map<String, Integer> instituteColumnMap = new LinkedHashMap<>();
            // The first column is year and the second column is month
            for (int i = 2; i < header.length; i++) {
                if (header[i] != null && !header[i].isEmpty()) {
                    List<String> instituteNames = extractInstituteNames(header[i]);

                    for (String instituteName: instituteNames) {
                        instituteColumnMap.put(instituteName, i);
                    }
                }
            }

            SupportAmountCSVReader supportAmountCSVReader = new SupportAmountCSVReader();
            supportAmountCSVReader.csvReader = csvReader;
            supportAmountCSVReader.instituteColumnMap = instituteColumnMap;

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

    public List<SupportAmount> read(List<Institute> availableInstitutes) {
        Map<String, Institute> availableInstNameMap = availableInstitutes.stream()
                .collect(Collectors.toMap(
                        Institute::getName,
                        Function.identity()
                ));

        List<SupportAmount> ret = new ArrayList<>();
        while (true) {
            try {
                String[] row = csvReader.readNext();
                if (row == null) {
                    return ret;
                }

                Year year = Year.of(Integer.parseInt(row[0]));
                Month month = Month.of(Integer.parseInt(row[1]));

                for (Map.Entry<String, Integer> e: instituteColumnMap.entrySet()) {
                    String instName = e.getKey();
                    Integer col = e.getValue();

                    if (!availableInstNameMap.containsKey(instName)) {
                        continue;
                    }

                    SupportAmount supportAmount = new SupportAmount();
                    supportAmount.setInstitute(availableInstNameMap.get(instName));
                    supportAmount.setYear(year);
                    supportAmount.setMonth(month);
                    supportAmount.setAmount(Integer.parseInt(row[col]));

                    ret.add(supportAmount);
                }
            } catch (IOException e) {
                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }
    }

    public int getNumOfInstitutes() {
        return this.instituteColumnMap.size();
    }

    public List<String> getInstituteNames() {
        return Collections.unmodifiableList(new ArrayList<>(this.instituteColumnMap.keySet()));
    }

    public Map<String, Integer> getInstituteColumnMap() {
        return instituteColumnMap;
    }
}
