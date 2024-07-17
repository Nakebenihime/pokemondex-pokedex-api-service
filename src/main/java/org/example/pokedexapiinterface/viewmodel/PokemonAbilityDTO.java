package org.example.pokedexapiinterface.viewmodel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class PokemonAbilityDTO extends RepresentationModel<PokemonAbilityDTO> {

    @NotBlank(message = "The \"name\" field is mandatory.")
    private String name;

    @NotBlank(message = "The \"description\" field is mandatory.")
    private String description;

    @NotBlank(message = "The \"hidden\" field is mandatory.")
    private Boolean hidden;
}
