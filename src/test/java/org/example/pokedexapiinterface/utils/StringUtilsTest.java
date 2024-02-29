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
    void testConvertToTitleCase_WithBrackets_SingleWord() {
        assertEquals("Charizard", StringUtils.convertToTitleCase("charizard", true));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_MultipleWords() {
        assertEquals("Charizard (Mega Charizard X)", StringUtils.convertToTitleCase("charizard-mega-charizard-x", true));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_SingleWord() {
        assertEquals("Adaptability", StringUtils.convertToTitleCase("adaptability", false));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_MultipleWords() {
        assertEquals("Air Lock", StringUtils.convertToTitleCase("air-lock", false));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_SingleWord_NoChange() {
        assertEquals("Charizard", StringUtils.convertToTitleCase("Charizard", true));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_SingleWord_NoChange() {
        assertEquals("Adaptability", StringUtils.convertToTitleCase("Adaptability", false));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertToTitleCase("", true));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertToTitleCase("", false));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertToTitleCase(null, true));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtils.convertToTitleCase(null, false));
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
