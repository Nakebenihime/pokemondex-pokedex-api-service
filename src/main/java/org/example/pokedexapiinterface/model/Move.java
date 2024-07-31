package org.example.pokedexapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "moves")
public class Move {

    @Id
    private String id;

    @Field(value = "name", targetType = FieldType.STRING)
    private String name;

    @Field(value = "type", targetType = FieldType.STRING)
    private PokemonType type;

    @Field(value = "category", targetType = FieldType.STRING)
    private String category;

    @Field(value = "power", targetType = FieldType.INT32)
    private Integer power;

    @Field(value = "accuracy", targetType = FieldType.INT32)
    private Integer accuracy;

    @Field(value = "pp", targetType = FieldType.INT32)
    private Integer pp;

    @Field(value = "description", targetType = FieldType.STRING)
    private String description;
}