package org.example.pokedexapiinterface.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PokemonTypeTests {

    static Stream<Object[]> provideValidInputs() {
        return Stream.of(
                new Object[]{"normal", PokemonType.NORMAL},
                new Object[]{"FIRE", PokemonType.FIRE},
                new Object[]{"Water", PokemonType.WATER},
                new Object[]{"icE", PokemonType.ICE}
        );
    }

    static Stream<Object[]> provideInvalidInputs() {
        return Stream.of(
                new Object[]{"unknown", NoSuchElementException.class},
                new Object[]{"UNKNOWN", NoSuchElementException.class},
                new Object[]{null, IllegalArgumentException.class},
                new Object[]{"", IllegalArgumentException.class}
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidInputs")
    void whenValidTypeIsGiven_thenReturnType(String input, PokemonType expected) {
        assertEquals(expected, PokemonType.getType(input));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputs")
    void whenInvalidTypeIsGiven_thenThrowException(String input, Class<Exception> expectedException) {
        assertThrows(expectedException, () -> PokemonType.getType(input));
    }
}