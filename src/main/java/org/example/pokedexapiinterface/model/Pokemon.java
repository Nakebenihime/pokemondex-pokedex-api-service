package org.example.pokedexapiinterface.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "pokemons")
public class Pokemon {

    @Id
    private String id;
    @Field(value = "ndex_id", targetType = FieldType.INT32)
    private int ndex;

    @Field(value = "name", targetType = FieldType.STRING)
    private String name;

    @Field(value = "url", targetType = FieldType.STRING)
    private String url;

    @Field(value = "types", targetType = FieldType.ARRAY)
    private ArrayList<PokemonType> pokemonTypes;

    private PokemonStats stats;

    @Field(value = "abilities", targetType = FieldType.ARRAY)
    private ArrayList<PokemonAbility> abilities;

    @Field(value = "moves", targetType = FieldType.ARRAY)
    private ArrayList<PokemonMove> moves;

    @Field(value = "types_charts", targetType = FieldType.ARRAY)
    private ArrayList<PokemonTypeCharts> charts;
}