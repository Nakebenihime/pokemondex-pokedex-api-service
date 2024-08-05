package org.example.pokedexapiinterface.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.service.IPokemonService;
import org.example.pokedexapiinterface.viewmodel.PokemonDTO;
import org.example.pokedexapiinterface.viewmodel.PokemonMinimalDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pokemons")
public class PokemonController {

    private final @NonNull IPokemonService pokemonService;

    public PokemonController(@NonNull IPokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<PagedModel<PokemonMinimalDTO>> getPokemons(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "ndex", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.info("Request path: '{}', Fetching all pokemons with pagination - Page Number: {}, Page Size: {}, Sort: {}",
                "/api/v1/pokemons", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return ResponseEntity.ok(pokemonService.findAll(pageable));
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<PokemonDTO> getPokemonByName(@PathVariable String name) {
        log.info("Request path: '{}', Fetching pokemon details for move with name: '{}'", "/api/v1/pokemons/" + name, name);
        return this.pokemonService.findByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PagedModel<PokemonMinimalDTO>> search(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> types) {
        log.info("Request path: '{}', Executing search for pokemons with parameters - Name: '{}', types: '{}'. " + "Pagination: Page Number: {}, Page Size: {}, Sort: {}",
                "/api/v1/pokemons/search/",
                name != null ? name : "N/A",
                types != null ? types : "N/A",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return ResponseEntity.ok(pokemonService.search(name, types, pageable));
    }
}