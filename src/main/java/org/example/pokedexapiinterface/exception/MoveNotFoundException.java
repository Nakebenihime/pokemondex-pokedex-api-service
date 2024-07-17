package org.example.pokedexapiinterface.exception;

public class MoveNotFoundException extends RuntimeException {
    public MoveNotFoundException() {
        super();
    }

    public MoveNotFoundException(String message) {
        super(message);
    }
}
