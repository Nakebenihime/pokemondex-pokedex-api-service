package org.example.pokedexapiinterface.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.exception.AbilityNotFoundException;
import org.example.pokedexapiinterface.model.Ability;
import org.example.pokedexapiinterface.repository.AbilityRepository;
import org.example.pokedexapiinterface.viewmodel.AbilityDTO;
import org.example.pokedexapiinterface.viewmodel.AbilityDTOAssembler;
import org.example.pokedexapiinterface.viewmodel.AbilityListDTOAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.pokedexapiinterface.utils.StringUtils.convertToTitleCase;

@Slf4j
@Service
public class IAbilityServiceImpl implements ISingleService<AbilityDTO> {

    private final @NonNull AbilityRepository abilityRepository;
    private final @NonNull AbilityDTOAssembler abilityDTOAssembler;

    private final @NonNull AbilityListDTOAssembler abilityListDTOAssembler;

    public IAbilityServiceImpl(@NonNull AbilityRepository abilityRepository, @NonNull AbilityDTOAssembler abilityDTOAssembler, @NonNull AbilityListDTOAssembler abilityListDTOAssembler) {
        this.abilityRepository = abilityRepository;
        this.abilityDTOAssembler = abilityDTOAssembler;
        this.abilityListDTOAssembler = abilityListDTOAssembler;
    }

    @Override
    public PagedModel<AbilityDTO> findAll(Pageable pageable) {
        Page<Ability> abilities = abilityRepository.findAll(pageable);
        if (abilities.isEmpty()) {
            throw new AbilityNotFoundException("This usually occurs when the pagination parameters are incorrect; please check the number of pages, the size and the sorting criteria. Example request: GET /api/abilities?page=2&size=20&sort=name,asc");
        }
        return abilityListDTOAssembler.toPagedModel(abilities);
    }

    @Override
    public Optional<AbilityDTO> findByName(String name) {
        return Optional.ofNullable(abilityRepository.findByName(convertToTitleCase(name)).map(abilityDTOAssembler::toModel)
                .orElseThrow(() -> new AbilityNotFoundException(String.format("This usually occurs when the specified Ability name (%s) can't be found, make sure the name is spelled correctly and includes any necessary hyphens (e.g., 'Charizard (Mega Charizard Y)'). Example request: GET /api/v1/abilities/charizard-mega-charizard-y", name))));
    }
}
