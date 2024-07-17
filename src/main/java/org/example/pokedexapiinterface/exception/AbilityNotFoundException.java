package org.example.pokedexapiinterface.exception;

public class AbilityNotFoundException extends RuntimeException {
    public AbilityNotFoundException() {
        super();
    }

    public AbilityNotFoundException(String message) {
        super(message);
    }
}