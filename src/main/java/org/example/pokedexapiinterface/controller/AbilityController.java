package org.example.pokedexapiinterface.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.service.ISingleService;
import org.example.pokedexapiinterface.viewmodel.AbilityDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/abilities")
public class AbilityController {

    private final @NonNull ISingleService<AbilityDTO> abilityService;

    public AbilityController(@NonNull ISingleService<AbilityDTO> abilityService) {
        this.abilityService = abilityService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<PagedModel<AbilityDTO>> getAbilities(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.info("/abilities : pageable: page {}, size {}, sort {}", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return ResponseEntity.ok(abilityService.findAll(pageable));
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<AbilityDTO> getAbilityByName(@PathVariable String name) {
        log.info("/pokemons/{}", name);
        return this.abilityService.findByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
