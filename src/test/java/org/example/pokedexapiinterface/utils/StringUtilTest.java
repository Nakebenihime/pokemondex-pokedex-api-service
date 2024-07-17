package org.example.pokedexapiinterface.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilTest {

    @Test
    void testConvertToHyphenatedForm_withSingleWord() {
        assertEquals("charizard", StringUtil.convertToHyphenatedForm("Charizard"));
    }

    @Test
    void testConvertToHyphenatedForm_withMultipleWords() {
        assertEquals("charizard-mega-charizard-x", StringUtil.convertToHyphenatedForm("Charizard (Mega Charizard X)"));
    }

    @Test
    void testConvertToHyphenatedForm_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToHyphenatedForm(null));
    }

    @Test
    void testConvertToHyphenatedForm_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToHyphenatedForm(""));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_SingleWord() {
        assertEquals("Charizard", StringUtil.convertToTitleCase("charizard", true));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_MultipleWords() {
        assertEquals("Charizard (Mega Charizard X)", StringUtil.convertToTitleCase("charizard-mega-charizard-x", true));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_SingleWord() {
        assertEquals("Adaptability", StringUtil.convertToTitleCase("adaptability", false));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_MultipleWords() {
        assertEquals("Air Lock", StringUtil.convertToTitleCase("air-lock", false));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_SingleWord_NoChange() {
        assertEquals("Charizard", StringUtil.convertToTitleCase("Charizard", true));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_SingleWord_NoChange() {
        assertEquals("Adaptability", StringUtil.convertToTitleCase("Adaptability", false));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToTitleCase("", true));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToTitleCase("", false));
    }

    @Test
    void testConvertToTitleCase_WithBrackets_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToTitleCase(null, true));
    }

    @Test
    void testConvertToTitleCase_WithoutBrackets_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToTitleCase(null, false));
    }

    @Test
    void testCapitalizeFirstLetter() {
        assertEquals("Charizard", StringUtil.capitalizeFirstLetter("charizard"));
    }

    @Test
    void testCapitalizeFirstLetter_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.capitalizeFirstLetter(null));
    }

    @Test
    void testCapitalizeFirstLetter_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.capitalizeFirstLetter(""));
    }
}
