package org.example.pokedexapiinterface.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Map;

@Builder
@Data
public class PokemonTypeCharts {
    @Field(value = "generation", targetType = FieldType.INT32)
    private int generation;
    @Field(value = "attack")
    private Map<PokemonType, Double> attack;
    @Field(value = "defense")
    private Map<PokemonType, Double> defense;
}
