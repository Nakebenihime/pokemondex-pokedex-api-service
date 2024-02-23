package org.example.pokedexapiinterface.viewmodel;

import lombok.NonNull;
import org.example.pokedexapiinterface.controller.MoveController;
import org.example.pokedexapiinterface.model.Move;
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
public class MoveDTOListAssembler extends RepresentationModelAssemblerSupport<Move, MoveDTO> {

    private final ModelMapper mapper;

    public MoveDTOListAssembler(ModelMapper mapper) {
        super(MoveController.class, MoveDTO.class);
        this.mapper = mapper;
    }

    @Override
    public @NonNull MoveDTO toModel(@NonNull Move entity) {
        MoveDTO moveDTO = mapper.map(entity, MoveDTO.class);
        moveDTO.add(linkTo(MoveController.class).slash((convertToHyphenatedForm(entity.getName()))).withSelfRel());
        return moveDTO;
    }

    @Override
    public @NonNull CollectionModel<MoveDTO> toCollectionModel(Iterable<? extends Move> entities) {
        return CollectionModel.of(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .toList());
    }

    public PagedModel<MoveDTO> toPagedModel(Page<Move> page) {
        Collection<MoveDTO> moveDTOS = toCollectionModel(page.getContent()).getContent();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<MoveDTO> pagedModel = PagedModel.of(moveDTOS, pageMetadata);
        pagedModel.add(linkTo(methodOn(MoveController.class).getMoves(page.getPageable())).withSelfRel());
        return pagedModel;
    }
}
