package org.example.pokedexapiinterface.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.service.IMoveService;
import org.example.pokedexapiinterface.viewmodel.MoveDTO;
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
@RequestMapping("/api/v1/moves")
public class MoveController {

    private final @NonNull IMoveService moveService;

    public MoveController(@NonNull IMoveService moveService) {
        this.moveService = moveService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<PagedModel<MoveDTO>> getMoves(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.info("Request path: '{}', Fetching all moves with pagination - Page Number: {}, Page Size: {}, Sort: {}",
                "/api/v1/moves", pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return ResponseEntity.ok(moveService.findAll(pageable));
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
    public ResponseEntity<MoveDTO> getMoveByName(@PathVariable String name) {
        log.info("Request path: '{}', Fetching moves details for move with name: '{}'", "/api/v1/moves/" + name, name);
        return this.moveService.findByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PagedModel<MoveDTO>> search(
            @PageableDefault(page = 0, size = 10)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer power,
            @RequestParam(required = false) Integer accuracy,
            @RequestParam(required = false) Integer pp,
            @RequestParam(required = false) String description) {
        log.info("Request path: '{}', Executing search for moves with parameters - Name: '{}', Type: '{}', Category: '{}',Power: '{}', Accuracy: '{}', PP: '{}', Description: '{}'. " + "Pagination: Page Number: {}, Page Size: {}, Sort: {}",
                "/api/v1/moves/search/",
                name != null ? name : "N/A",
                type != null ? type : "N/A",
                category != null ? category : "N/A",
                power != null ? power : "N/A",
                accuracy != null ? accuracy : "N/A",
                pp != null ? pp : "N/A",
                description != null ? description : "N/A",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return ResponseEntity.ok(moveService.search(name, type, category, power, accuracy, pp, description, pageable));
    }
}