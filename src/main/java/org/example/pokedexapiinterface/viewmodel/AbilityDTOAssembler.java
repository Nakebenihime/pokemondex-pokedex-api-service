package org.example.pokedexapiinterface.viewmodel;

import lombok.NonNull;
import org.example.pokedexapiinterface.controller.AbilityController;
import org.example.pokedexapiinterface.model.Ability;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.example.pokedexapiinterface.utils.StringUtil.convertToHyphenatedForm;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AbilityDTOAssembler extends RepresentationModelAssemblerSupport<Ability, AbilityDTO> {

    private final ModelMapper mapper;

    public AbilityDTOAssembler(ModelMapper mapper) {
        super(AbilityController.class, AbilityDTO.class);
        this.mapper = mapper;
    }

    @Override
    public @NonNull AbilityDTO toModel(@NonNull Ability entity) {
        AbilityDTO abilityDTO = mapper.map(entity, AbilityDTO.class);
        abilityDTO.add(linkTo(methodOn(AbilityController.class).getAbilities(Pageable.unpaged())).withSelfRel());
        return abilityDTO;
    }

    public @NonNull AbilityDTO toList(@NonNull Ability entity) {
        AbilityDTO abilityDTO = mapper.map(entity, AbilityDTO.class);
        abilityDTO.add(linkTo(AbilityController.class).slash((convertToHyphenatedForm(entity.getName()))).withSelfRel());
        return abilityDTO;
    }

}
