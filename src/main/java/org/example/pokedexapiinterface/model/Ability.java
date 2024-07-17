package org.example.pokedexapiinterface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "abilities")
public class Ability {

    @Id
    private String id;

    @Field(value = "name", targetType = FieldType.STRING)
    private String name;

    @Field(value = "description", targetType = FieldType.STRING)
    private String description;

    @Field(value = "introduced_generation", targetType = FieldType.INT32)
    private int generation;
}