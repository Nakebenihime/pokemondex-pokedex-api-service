package org.example.pokedexapiinterface.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Builder
@Data
public class PokemonStats {
    @Field(value = "hp", targetType = FieldType.INT32)
    private Number hp;
    @Field(value = "attack", targetType = FieldType.INT32)
    private Number atk;
    @Field(value = "defense", targetType = FieldType.INT32)
    private Number def;
    @Field(value = "spattack", targetType = FieldType.INT32)
    private Number spAtk;
    @Field(value = "spdefense", targetType = FieldType.INT32)
    private Number spDef;
    @Field(value = "speed", targetType = FieldType.INT32)
    private Number speed;
    @Field(value = "total", targetType = FieldType.INT32)
    private Number total;
}
