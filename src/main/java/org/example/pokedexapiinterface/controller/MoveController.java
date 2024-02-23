package org.example.pokedexapiinterface.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.service.ISingleService;
import org.example.pokedexapiinterface.viewmodel.MoveDTO;
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
@RequestMapping("/api/v1/moves")
public class MoveController {

    private final @NonNull ISingleService<MoveDTO> moveService;

    public MoveController(@NonNull ISingleService<MoveDTO> moveService) {
        this.moveService = moveService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<PagedModel<MoveDTO>> getMoves(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.info("/moves : pageable: page {}, size {}, sort {}", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return ResponseEntity.ok(moveService.findAll(pageable));
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<MoveDTO> getMoveByName(@PathVariable String name) {
        log.info("/moves/{}", name);
        return this.moveService.findByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}