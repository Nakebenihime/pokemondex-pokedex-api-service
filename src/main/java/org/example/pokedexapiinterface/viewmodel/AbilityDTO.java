package org.example.pokedexapiinterface.viewmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "abilities")
@Data
public class AbilityDTO extends RepresentationModel<AbilityDTO> {

    @NotBlank(message = "The \"name\" field is mandatory.")
    private String name;

    @NotBlank(message = "The \"description\" field is mandatory.")
    private String description;

    @NotNull(message = "The \"generation\" field is mandatory.")
    private Integer generation;
}