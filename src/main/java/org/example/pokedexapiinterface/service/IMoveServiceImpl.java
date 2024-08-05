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
public class IMoveServiceImpl implements IMoveService {

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
        log.info("Fetching all moves with pagination - Page Number: {}, Page Size: {}, Sort: {}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Move> moves = moveRepository.findAll(pageable);
        if (moves.isEmpty()) {
            throw new MoveNotFoundException(
                    "The query yielded no results. " +
                            "This usually occurs when the pagination settings are not correctly configured, including the page number, page size, or sorting order. " +
                            "For example, you might try: GET /api/moves?page=1&size=10&sort=name,asc or GET /api/moves?page=1&size=20&sort=generation,desc");
        }
        log.info("Successfully retrieved {} moves matching the query.", moves.getTotalElements());
        return pagedResourcesAssembler.toModel(moves, moveDTOAssembler::toList);
    }

    @Override
    public Optional<MoveDTO> findByName(String name) {
        return Optional.ofNullable(moveRepository.findByName(convertToTitleCase(name, false)).map(moveDTOAssembler::toModel)
                .orElseThrow(() -> new MoveNotFoundException(String.format(
                        "The specified Move name ('%s') could not be found. " +
                                "To avoid this issue, ensure that the name is spelled correctly and that any spaces in the name are replaced with hyphens. " +
                                "For example, if you're trying to access the Move 'Aerial Ace', you should format it as 'aerial-ace' in your request, like so: GET /api/v1/moves/aerial-ace", name))
                )
        );
    }

    @Override
    public PagedModel<MoveDTO> search(String name, String type, String category, Integer power, Integer accuracy, Integer pp, String description, Pageable pageable) {
        Page<Move> moves = moveRepository.search(name, type, category, power, accuracy, pp, description, pageable);
        if (moves.isEmpty()) {
            throw new MoveNotFoundException(
                    "The query yielded no results. " +
                            "This usually occurs when the search or pagination settings are not correctly configured. " +
                            "For example, you might try: GET /api/v1/moves/search?type=FIRE&category=physical&page=0&size=10&sort=power,desc");
        }
        log.info("Successfully retrieved {} moves matching the query.", moves.getTotalElements());
        return pagedResourcesAssembler.toModel(moves, moveDTOAssembler::toList);
    }
}
