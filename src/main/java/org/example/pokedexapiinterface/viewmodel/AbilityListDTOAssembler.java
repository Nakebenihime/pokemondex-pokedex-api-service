package org.example.pokedexapiinterface.viewmodel;

import lombok.NonNull;
import org.example.pokedexapiinterface.controller.AbilityController;
import org.example.pokedexapiinterface.controller.PokemonController;
import org.example.pokedexapiinterface.model.Ability;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.StreamSupport;

import static org.example.pokedexapiinterface.utils.StringUtils.convertToHyphenatedForm;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AbilityListDTOAssembler extends RepresentationModelAssemblerSupport<Ability, AbilityDTO> {

    private final ModelMapper mapper;

    public AbilityListDTOAssembler(ModelMapper mapper) {
        super(AbilityController.class, AbilityDTO.class);
        this.mapper = mapper;
    }

    @Override
    public @NonNull AbilityDTO toModel(@NonNull Ability entity) {
        AbilityDTO abilityDTO = mapper.map(entity, AbilityDTO.class);
        abilityDTO.add(linkTo(AbilityController.class).slash((convertToHyphenatedForm(entity.getName()))).withSelfRel());
        return abilityDTO;
    }

    @Override
    public @NonNull CollectionModel<AbilityDTO> toCollectionModel(Iterable<? extends Ability> entities) {
        return CollectionModel.of(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .toList());
    }

    public PagedModel<AbilityDTO> toPagedModel(Page<Ability> page) {
        Collection<AbilityDTO> abilityDTOS = toCollectionModel(page.getContent()).getContent();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<AbilityDTO> pagedModel = PagedModel.of(abilityDTOS, pageMetadata);
        pagedModel.add(linkTo(methodOn(AbilityController.class).getAbilities(page.getPageable())).withSelfRel());
        return pagedModel;
    }
}
