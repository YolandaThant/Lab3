package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// y TODO CheckStyle: Wrong lexicographical order for 'java.util.HashMap' import (remove this comment once resolved)

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // y TODO Task: pick appropriate instance variable(s) to store the data necessary for this class
    private Map<String, String> countryCodeMap;
    private Map<String, String> codeCountryMap;
    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */

    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        countryCodeMap = new HashMap<>();
        codeCountryMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // y TODO Task: use lines to populate the instance variable(s)
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split("\t");
                countryCodeMap.put(parts[0], parts[2]);
                codeCountryMap.put(parts[2], parts[0]);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // y TODO Task: update this code to use an instance variable to return the correct value
        return codeCountryMap.get(code.toUpperCase());
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // y TODO Task: update this code to use an instance variable to return the correct value
        return countryCodeMap.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // y TODO Task: update this code to use an instance variable to return the correct value
        return countryCodeMap.size();
    }
    /**
     * Checks code for CountryCodeConverter.
     */

    public static void main(String[] args) {
        // Test the functionality of the CountryCodeConverter
        try {
            // Instantiate the converter with default filename
            CountryCodeConverter converter = new CountryCodeConverter();

            // Test converting Alpha-3 code to country name
            String countryName = converter.fromCountryCode("usa");
            System.out.println("USA -> " + countryName);

            // Test converting country name to Alpha-3 code
            String countryCode = converter.fromCountry("United States of America (the)");
            System.out.println("United States of America (the) -> " + countryCode);

            // Test how many countries are loaded
            int totalCountries = converter.getNumCountries();
            System.out.println("Total countries loaded: " + totalCountries);

        }
        catch (RuntimeException e) {
            System.err.println("Error loading the country codes: " + e.getMessage());
        }
    }
}
