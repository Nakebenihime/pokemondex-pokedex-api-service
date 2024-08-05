package org.example.pokedexapiinterface.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.service.IAbilityService;
import org.example.pokedexapiinterface.viewmodel.AbilityDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/abilities")
public class AbilityController {

    private final @NonNull IAbilityService abilityService;

    public AbilityController(@NonNull IAbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<PagedModel<AbilityDTO>> getAbilities(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.info("Request path: '{}', Fetching all abilities with pagination - Page Number: {}, Page Size: {}, Sort: {}",
                "/api/v1/abilities", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return ResponseEntity.ok(abilityService.findAll(pageable));
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<AbilityDTO> getAbilityByName(@PathVariable String name) {
        log.info("Request path: '{}', Fetching ability details for ability with name: '{}'", "/api/v1/abilities/" + name, name);
        return this.abilityService.findByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PagedModel<AbilityDTO>> search(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer generation) {
        log.info("Request path: '{}', Executing search for abilities with parameters - Name: '{}', Description: '{}', Generation: '{}'. " + "Pagination: Page Number: {}, Page Size: {}, Sort: {}",
                "/api/v1/abilities/search/",
                name != null ? name : "N/A",
                description != null ? description : "N/A",
                generation != null ? generation : "N/A",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return ResponseEntity.ok(abilityService.search(name, description, generation, pageable));
    }
}
