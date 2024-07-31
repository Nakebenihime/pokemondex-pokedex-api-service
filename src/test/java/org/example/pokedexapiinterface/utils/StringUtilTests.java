package org.example.pokedexapiinterface.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilTests {


    static Stream<Object[]> provideValidHyphenatedInputs() {
        return Stream.of(
                new Object[]{"Charizard", "charizard"},
                new Object[]{"Charizard (Mega Charizard X)", "charizard-mega-charizard-x"}
        );
    }

    static Stream<Object[]> provideValidTitleCaseInputs() {
        return Stream.of(
                new Object[]{"charizard", true, "Charizard"},
                new Object[]{"charizard-mega-charizard-x", true, "Charizard (Mega Charizard X)"},
                new Object[]{"adaptability", false, "Adaptability"},
                new Object[]{"air-lock", false, "Air Lock"},
                new Object[]{"Charizard", true, "Charizard"},
                new Object[]{"Adaptability", false, "Adaptability"}
        );
    }

    static Stream<String> provideInvalidInputs() {
        return Stream.of(
                null,
                ""
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidHyphenatedInputs")
    void whenConvertToHyphenatedFormIsCalled_thenReturnHyphenatedForm(String input, String expected) {
        assertEquals(expected, StringUtil.convertToHyphenatedForm(input));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputs")
    void whenConvertToHyphenatedFormIsCalledWithInvalidInput_thenThrowException(String input) {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToHyphenatedForm(input));
    }

    @ParameterizedTest
    @MethodSource("provideValidTitleCaseInputs")
    void whenConvertToTitleCaseIsCalled_thenReturnTitleCase(String input, boolean useBrackets, String expected) {
        assertEquals(expected, StringUtil.convertToTitleCase(input, useBrackets));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputs")
    void whenConvertToTitleCaseIsCalledWithInvalidInput_thenThrowException(String input) {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToTitleCase(input, true));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputs")
    void whenConvertToTitleCaseIsCalledWithInvalidInputWithoutBrackets_thenThrowException(String input) {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.convertToTitleCase(input, false));
    }

    @ParameterizedTest
    @CsvSource({
            "charizard, Charizard",
            "adaptability, Adaptability"
    })
    void whenCapitalizeFirstLetterIsCalled_thenReturnCapitalizedString(String input, String expected) {
        assertEquals(expected, StringUtil.capitalizeFirstLetter(input));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputs")
    void whenCapitalizeFirstLetterIsCalledWithInvalidInput_thenThrowException(String input) {
        assertThrows(IllegalArgumentException.class, () -> StringUtil.capitalizeFirstLetter(input));
    }
}
