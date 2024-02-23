package org.example.pokedexapiinterface.utils;

import java.util.StringJoiner;

public class StringUtils {

    private StringUtils() {
    }

    /**
     * Converts the word string to hyphenated form.
     *
     * <p>The method converts the word string to lowercase and replaces any non-alphanumeric characters
     * with hyphens. Leading and trailing hyphens are removed.
     * For example, "Charizard (Mega Charizard X)" will be converted to "charizard-mega-charizard-x".
     *
     * @param word the word string to be converted
     * @return the word string converted to hyphenated form
     * @throws IllegalArgumentException if the word string is null or empty
     */
    public static String convertToHyphenatedForm(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Input word cannot be null or empty");
        }
        return word.toLowerCase().replaceAll("[^a-zA-Z0-9]+", "-").replaceAll("^-|-$", "");
    }

    /**
     * Converts a hyphen-separated word string to title case.
     *
     * <p>Title case capitalizes the first letter of each word in the word string.
     * If the word contains hyphens, the first word before the hyphen will be separately capitalized
     * and enclosed in parentheses, while the remaining words will be title cased.
     * For example, "charizard-mega-charizard-x" will be converted to "Charizard (Mega Charizard X)".
     *
     * @param word the hyphen-separated word string to be converted
     * @return the word string converted to title case
     * @throws IllegalArgumentException if the word string is null or empty
     */
    public static String convertToTitleCaseWBrackets(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Input word cannot be null or empty");
        }

        String[] parts = word.split("-");
        StringJoiner titleCaseString = new StringJoiner(" ");

        for (String part : parts) {
            titleCaseString.add(capitalizeFirstLetter(part));
        }

        String res = titleCaseString.toString();

        if (parts.length > 1) {
            res = capitalizeFirstLetter(parts[0]) + " (" + res.substring(parts[0].length() + 1) + ")";
        }
        return res;
    }

    public static String convertToTitleCaseWOBrackets(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Input word cannot be null or empty");
        }

        String[] parts = word.split("-");
        StringJoiner titleCaseString = new StringJoiner(" ");

        for (String part : parts) {
            titleCaseString.add(capitalizeFirstLetter(part));
        }
        return titleCaseString.toString();
    }

    /**
     * Capitalizes the first letter of the given word and converts the rest of the word to lowercase.
     *
     * @param word the word to be capitalized
     * @return the word with the first letter capitalized and the rest of the letters in lowercase
     * @throws IllegalArgumentException if the input word is null or empty
     */
    public static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Input word cannot be null or empty");
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }
}
