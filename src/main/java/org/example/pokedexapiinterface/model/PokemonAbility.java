package org.example.pokedexapiinterface.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Builder
@Data
public class PokemonAbility {

    @Field(value = "name", targetType = FieldType.STRING)
    private String name;
    @Field(value = "description", targetType = FieldType.STRING)
    private String description;
    @Field(value = "hidden", targetType = FieldType.BOOLEAN)
    private Boolean hidden;
}
