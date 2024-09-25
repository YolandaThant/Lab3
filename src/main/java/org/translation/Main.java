package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // TODO Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        Translator translator = new JSONTranslator("sample.json");
        //        Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        final String quit = "quit";

        while (true) {
            String country = promptForCountry(translator);
            // y TODO CheckStyle: The String "quit" appears 3 times in the file.
            // y TODO Checkstyle: String literal expressions should be on the left side of an equals comparison
            if (quit.equals(country)) {
                break;
            }
            // TODO Task: Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            CountryCodeConverter countryConvert = new CountryCodeConverter();
            String countryCode = countryConvert.fromCountry(country);

            String language = promptForLanguage(translator, countryCode);
            if (quit.equals(language)) {
                break;
            }
            // TODO Task: Once you switch promptForLanguage so that it returns the language
            //            name rather than the 2-letter language code, you will need to
            //            convert it back to its 2-letter language code when calling translate.
            //            Note: you should use the actual names in the message printed below though,
            //            since the user will see the displayed message.
            LanguageCodeConverter languageConvert = new LanguageCodeConverter();
            String languageCode = languageConvert.fromLanguage(language);

            System.out.println(country + " in " + language + " is " + translator.translate(countryCode, languageCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        // y TODO Task: replace the following println call, sort the countries alphabetically,
        //            and print them out; one per line
        //      hint: class Collections provides a static sort method
        // y TODO Task: convert the country codes to the actual country names before sorting
        List<String> countries = translator.getCountries();
        List<String> countryName = new ArrayList<>();
        for (String country : countries) {
            CountryCodeConverter countryCodeConvert = new CountryCodeConverter();
            countryName.add(countryCodeConvert.fromCountryCode(country));
            // System.out.println(countryCodeConvert.fromCountryCode(country));
        }
        Collections.sort(countryName);
        for (String country : countryName) {
            System.out.println(country);
        }
        // System.out.println(countryName.size());
        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        // y TODO Task: replace the line below so that we sort the languages alphabetically
        //  and print them out; one per line
        // y TODO Task: convert the language codes to the actual language names before sorting
        //        System.out.println(translator.getCountryLanguages(country));
        List<String> languages = translator.getCountryLanguages(country);
        // System.out.println(languages);
        List<String> languageName = new ArrayList<>();
        for (String language : languages) {
            LanguageCodeConverter languageCodeConvert = new LanguageCodeConverter();
            languageName.add(languageCodeConvert.fromLanguageCode(language));
            // System.out.println(languageCodeConvert.fromLanguageCode(language));
        }
        Collections.sort(languageName);
        for (String language : languageName) {
            System.out.println(language);
        }
        //        System.out.println(languageName.size());

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
