package org.example.pokedexapiinterface.viewmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "pokemons")
@Data
public class PokemonMinimalDTO extends RepresentationModel<PokemonMinimalDTO> {

    @NotNull(message = "The \"ndex\" field is mandatory.")
    private int ndex;

    @NotBlank(message = "The \"name\" field is mandatory.")
    private String name;
}
