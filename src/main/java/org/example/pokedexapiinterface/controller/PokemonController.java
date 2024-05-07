package org.example.pokedexapiinterface.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.service.IService;
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

    private final @NonNull IService<PokemonDTO, PokemonMinimalDTO> pokemonService;

    public PokemonController(@NonNull IService<PokemonDTO, PokemonMinimalDTO> pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public PagedModel<PokemonMinimalDTO> getPokemons(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "ndex", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.info("/pokemons : pageable: page {}, size {}, sort {}", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return pokemonService.findAll(pageable);
    }

    @GetMapping(params = {"types"}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<PagedModel<PokemonMinimalDTO>> getPokemonsByType(
            @RequestParam(defaultValue = "") List<String> types,
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "ndex", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.info("/pokemons : pageable: page {}, size {}, sort {}, filter: types {}", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort(), types);
        return ResponseEntity.ok(pokemonService.findAllByPokemonTypes(pageable, types));
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<PokemonDTO> getPokemonByName(@PathVariable String name) {
        log.info("/pokemons/{}", name);
        return this.pokemonService.findByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}