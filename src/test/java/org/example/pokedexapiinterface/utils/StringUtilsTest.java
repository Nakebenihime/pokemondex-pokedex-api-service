package org.example.pokedexapiinterface.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilsTest {

    @Test
    void testConvertToHyphenatedForm_withSingleWord() {
        assertEquals("charizard", StringUtils.convertToHyphenatedForm("Charizard"));
    }

    @Test
    void testConvertToHyphenatedForm_withMultipleWords() {
        assertEquals("charizard-mega-charizard-x", StringUtils.convertToHyphenatedForm("Charizard (Mega Charizard X)"));
    }

    @Test
    void testConvertToHyphenatedForm_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertToHyphenatedForm(null));
    }

    @Test
    void testConvertToHyphenatedForm_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertToHyphenatedForm(""));
    }

    @Test
    void testConvertToTitleCase_withSingleWord() {
        assertEquals("Charizard", StringUtils.convertToTitleCaseWBrackets("charizard"));
    }

    @Test
    void testConvertToTitleCase_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertToTitleCaseWBrackets(null));
    }

    @Test
    void testConvertToTitleCase_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertToTitleCaseWBrackets(""));
    }

    @Test
    void testConvertToTitleCase_withMultipleWords() {
        assertEquals("Charizard (Mega Charizard X)", StringUtils.convertToTitleCaseWBrackets("charizard-mega-charizard-x"));
    }

    @Test
    void testCapitalizeFirstLetter() {
        assertEquals("Charizard", StringUtils.capitalizeFirstLetter("charizard"));
    }

    @Test
    void testCapitalizeFirstLetter_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.capitalizeFirstLetter(null));
    }

    @Test
    void testCapitalizeFirstLetter_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.capitalizeFirstLetter(""));
    }

}
