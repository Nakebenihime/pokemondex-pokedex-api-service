package org.example.pokedexapiinterface.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.exception.MoveNotFoundException;
import org.example.pokedexapiinterface.model.Move;
import org.example.pokedexapiinterface.repository.MoveRepository;
import org.example.pokedexapiinterface.viewmodel.MoveDTO;
import org.example.pokedexapiinterface.viewmodel.MoveDTOAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.pokedexapiinterface.utils.StringUtil.convertToTitleCase;

@Slf4j
@Service
public class IMoveServiceImpl implements ISingleService<MoveDTO> {

    private final @NonNull MoveRepository moveRepository;
    private final @NonNull MoveDTOAssembler moveDTOAssembler;
    private final @NonNull PagedResourcesAssembler<Move> pagedResourcesAssembler;

    public IMoveServiceImpl(@NonNull MoveRepository moveRepository, @NonNull MoveDTOAssembler moveDTOAssembler, @NonNull PagedResourcesAssembler<Move> pagedResourcesAssembler) {
        this.moveRepository = moveRepository;
        this.moveDTOAssembler = moveDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedModel<MoveDTO> findAll(Pageable pageable) {
        Page<Move> moves = moveRepository.findAll(pageable);
        if (moves.isEmpty()) {
            throw new MoveNotFoundException("This usually occurs when the pagination parameters are incorrect; please check the number of pages, the size and the sorting criteria. Example request: GET /api/moves?page=2&size=20&sort=name,asc");
        }
        return pagedResourcesAssembler.toModel(moves, moveDTOAssembler::toList);
    }

    @Override
    public Optional<MoveDTO> findByName(String name) {
        return Optional.ofNullable(moveRepository.findByName(convertToTitleCase(name, false)).map(moveDTOAssembler::toModel)
                .orElseThrow(() -> new MoveNotFoundException(String.format("This usually occurs when the specified Move name (%s) can't be found, make sure the name is spelled correctly and includes any necessary hyphens (e.g., 'Aerial Ace'). Example request: GET /api/v1/moves/aerial-ace", name))));
    }
}
