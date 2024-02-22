package org.example.pokedexapiinterface.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Builder
@Data
public class PokemonMove {

    @Field(value = "learned_at", targetType = FieldType.INT32)
    private int learnedAt;
    @Field(value = "name", targetType = FieldType.STRING)
    private String name;
}
