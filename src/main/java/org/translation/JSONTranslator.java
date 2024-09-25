package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private final JSONArray jsonArray;
    private final String ALPHA3 = "alpha3";
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */

    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            this.jsonArray = new JSONArray(jsonString);
            // y TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // y TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> languages = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.getString(ALPHA3).equalsIgnoreCase(country)) {
                for (String key : jsonObject.keySet()) {
                    if (!("id".equals(key) || "alpha2".equals(key) || ALPHA3.equals(key))) {
                        languages.add(key);
                    }
                }
                break;
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        // y TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        ArrayList<String> countries = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject line = jsonArray.getJSONObject(i);
            countries.add(line.getString(ALPHA3));
        }
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        // y TODO Task: complete this method using your instance variables as needed
        String result = "Not found";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject line = jsonArray.getJSONObject(i);
            if (line.getString(ALPHA3).equalsIgnoreCase(country)) {
                if (line.has(language.toLowerCase())) {
                    result = line.getString(language.toLowerCase());
                }
                else {
                    result = "Not available";
                }
            }
        }
        return result;
    }
}
