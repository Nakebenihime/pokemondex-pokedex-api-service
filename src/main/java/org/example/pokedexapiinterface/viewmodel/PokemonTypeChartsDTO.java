package org.example.pokedexapiinterface.viewmodel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.pokedexapiinterface.model.PokemonType;

import java.util.Map;

@Data
public class PokemonTypeChartsDTO {

    @NotNull(message = "The \"generation\" field is mandatory.")
    @Min(value = 1, message = "The \"generation\" field must be an integer equal to or greater than 0 and is mandatory.")
    private int generation;

    @NotNull(message = "The \"attack\" object is mandatory.")
    private Map<PokemonType, Double> attack;

    @NotNull(message = "The \"defense\" object is mandatory.")
    private Map<PokemonType, Double> defense;
}
