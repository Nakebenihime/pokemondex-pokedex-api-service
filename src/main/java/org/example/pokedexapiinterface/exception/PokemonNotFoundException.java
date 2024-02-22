package org.example.pokedexapiinterface.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException() {
        super();
    }

    public PokemonNotFoundException(String message) {
        super(message);
    }
}