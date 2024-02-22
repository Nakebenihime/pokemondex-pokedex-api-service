package org.example.pokedexapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@ToString
public enum PokemonType {
    NORMAL("normal"),
    FIRE("fire"),
    WATER("water"),
    ELECTRIC("electric"),
    GRASS("grass"),
    ICE("ice"),
    FIGHTING("fighting"),
    POISON("poison"),
    GROUND("ground"),
    FLYING("flying"),
    PSYCHIC("psychic"),
    BUG("bug"),
    ROCK("rock"),
    GHOST("ghost"),
    DRAGON("dragon"),
    DARK("dark"),
    STEEL("steel"),
    FAIRY("fairy");

    private final String type;

    public static PokemonType getType(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Input value cannot be null or empty");
        }
        return Stream.of(PokemonType.values())
                .filter(pokemonType -> value.toLowerCase().equals(pokemonType.getType()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
