package org.example.pokedexapiinterface.viewmodel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class PokemonMoveDTO extends RepresentationModel<PokemonMoveDTO> {

    @NotNull(message = "The \"learnedAt\" field is mandatory.")
    @Min(0)
    @Max(100)
    private int learnedAt;

    @NotBlank(message = "The \"name\" field is mandatory.")
    private String name;
}
