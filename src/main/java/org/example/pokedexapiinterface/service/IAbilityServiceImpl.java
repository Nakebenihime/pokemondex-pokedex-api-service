package org.example.pokedexapiinterface.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.exception.AbilityNotFoundException;
import org.example.pokedexapiinterface.model.Ability;
import org.example.pokedexapiinterface.repository.AbilityRepository;
import org.example.pokedexapiinterface.viewmodel.AbilityDTO;
import org.example.pokedexapiinterface.viewmodel.AbilityDTOAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.pokedexapiinterface.utils.StringUtil.convertToTitleCase;

@Slf4j
@Service
public class IAbilityServiceImpl implements IAbilityService {

    private final @NonNull AbilityRepository abilityRepository;
    private final @NonNull AbilityDTOAssembler abilityDTOAssembler;
    private final @NonNull PagedResourcesAssembler<Ability> pagedResourcesAssembler;

    public IAbilityServiceImpl(@NonNull AbilityRepository abilityRepository, @NonNull AbilityDTOAssembler abilityDTOAssembler, @NonNull PagedResourcesAssembler<Ability> pagedResourcesAssembler) {
        this.abilityRepository = abilityRepository;
        this.abilityDTOAssembler = abilityDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedModel<AbilityDTO> findAll(Pageable pageable) {
        log.info("Fetching all abilities with pagination - Page Number: {}, Page Size: {}, Sort: {}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Ability> abilities = abilityRepository.findAll(pageable);
        if (abilities.isEmpty()) {
            throw new AbilityNotFoundException(
                    "The query yielded no results. " +
                            "This usually occurs when the pagination settings are not correctly configured, including the page number, page size, or sorting order. " +
                            "For example, you might try: GET /api/abilities?page=1&size=10&sort=name,asc or GET /api/abilities?page=1&size=20&sort=generation,desc");
        }
        log.info("Successfully retrieved {} abilities matching the query.", abilities.getTotalElements());
        return pagedResourcesAssembler.toModel(abilities, abilityDTOAssembler::toList);
    }

    @Override
    public Optional<AbilityDTO> findByName(String name) {
        return Optional.ofNullable(
                abilityRepository.findByName(convertToTitleCase(name, false))
                        .map(abilityDTOAssembler::toModel)
                        .orElseThrow(() -> new AbilityNotFoundException(String.format(
                                "The specified Ability name ('%s') could not be found. " +
                                        "To avoid this issue, ensure that the name is spelled correctly and that any spaces in the name are replaced with hyphens. " +
                                        "For example, if you're trying to access the Ability 'Armor Tail', you should format it as 'armor-tail' in your request, like so: GET /api/v1/abilities/armor-tail", name))
                        )
        );
    }

    @Override
    public PagedModel<AbilityDTO> search(String name, String description, Integer generation, Pageable pageable) {
        Page<Ability> abilities = abilityRepository.search(name, description, generation, pageable);
        if (abilities.isEmpty()) {
            throw new AbilityNotFoundException(
                    "The query yielded no results. " +
                            "This usually occurs when the search or pagination settings are not correctly configured. " +
                            "For example, you might try: GET /api/abilities/search?name=Armor&generation=3&page=0&size=10&sort=generation,desc");
        }
        log.info("Successfully retrieved {} abilities matching the query.", abilities.getTotalElements());
        return pagedResourcesAssembler.toModel(abilities, abilityDTOAssembler::toList);
    }
}
