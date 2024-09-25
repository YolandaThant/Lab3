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
    private final Map<String, Map<String, String>> translations;
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
            JSONArray jsonArray = new JSONArray(jsonString);

            // y TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            translations = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String countryCode = jsonObject.getString("alpha3");

                // new Map to store lang trans for this country
                Map<String, String> languageMap = new HashMap<>();
                for (String key : jsonObject.keySet()) {
                    if (!("id".equals(key) || "alpha2".equals(key) || "alpha3".equals(key))) {
                        //                        languageMap.put(key, jsonObject.getString(key));
                        languageMap.put(key, jsonObject.optString(key, null));
                    }
                }
                translations.put(countryCode, languageMap);
                // System.out.println(translations);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // y TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        Map<String, String> languageMap = translations.get(country);
        // System.out.println(languageMap);
        if (languageMap == null) {
            return new ArrayList<>();
        }
        List<String> languages = new ArrayList<>(languageMap.keySet());
        System.out.println("Languages for country " + country + ": " + languageMap);
        System.out.println(languageMap.keySet());
        return new ArrayList<>(languageMap.keySet());
    }

    @Override
    public List<String> getCountries() {
        // y TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(translations.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // y TODO Task: complete this method using your instance variables as needed
        Map<String, String> languageMap = translations.get(country);
        if (languageMap != null && languageMap.containsKey(language)) {
            return languageMap.get(language);
        }
        return null;
    }
}
