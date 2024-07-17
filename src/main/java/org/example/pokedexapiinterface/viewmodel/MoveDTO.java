package org.example.pokedexapiinterface.viewmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.pokedexapiinterface.model.PokemonType;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "moves")
@Data
public class MoveDTO extends RepresentationModel<MoveDTO> {

    @NotBlank(message = "The \"name\" field is mandatory.")
    private String name;

    @NotBlank(message = "The \"type\" field is mandatory.")
    private PokemonType type;

    @NotBlank(message = "The \"category\" field is mandatory.")
    private String category;

    @NotNull(message = "The \"power\" field is mandatory.")
    private int power;

    @NotNull(message = "The \"accuracy\" field is mandatory.")
    private int accuracy;

    @NotNull(message = "The \"pp\" field is mandatory.")
    private int pp;

    @NotBlank(message = "The \"description\" field is mandatory.")
    private String description;
}
