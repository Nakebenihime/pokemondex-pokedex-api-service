package org.example.pokedexapiinterface.viewmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.pokedexapiinterface.model.PokemonType;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = false)
@Data
public class PokemonDTO extends RepresentationModel<PokemonDTO> {

    @NotNull(message = "The \"id\" field is mandatory.")
    private int ndex;

    @NotBlank(message = "The \"name\" field is mandatory.")
    private String name;

    @NotBlank(message = "The \"url\" field is mandatory.")
    private String url;

    @NotNull(message = "The \"pokemon types\" array is mandatory.")
    @Size(min = 1, max = 2)
    private ArrayList<PokemonType> types;

    @NotNull(message = "The \"pokemon stats\" object is mandatory.")
    private PokemonStatsDTO stats;

    @NotNull(message = "The \"pokemon abilities\" array is mandatory.")
    private ArrayList<PokemonAbilityDTO> abilities;

    @NotNull(message = "The \"pokemon moves\" array is mandatory.")
    private ArrayList<PokemonMoveDTO> moves;

    @NotNull(message = "The \"pokemon charts\" array is mandatory.")
    private ArrayList<PokemonTypeChartsDTO> charts;
}
