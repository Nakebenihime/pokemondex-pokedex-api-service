package org.example.pokedexapiinterface.model;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PokemonPokemonTypeTest {

    @Test
    void testGetType_ValidType() {
        PokemonType firePokemonType = PokemonType.getType("fire");
        assertEquals(PokemonType.FIRE, firePokemonType);
    }

    @Test
    void testGetType_InvalidType() {
        assertThrows(NoSuchElementException.class, () -> PokemonType.getType("light"));
    }

    @Test
    void testGetType_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> PokemonType.getType(null));
    }

    @Test
    void testGetType_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> PokemonType.getType(""));
    }
}