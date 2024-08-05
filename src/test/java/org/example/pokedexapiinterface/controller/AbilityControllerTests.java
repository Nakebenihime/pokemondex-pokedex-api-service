package org.example.pokedexapiinterface.controller;

import org.example.pokedexapiinterface.exception.AbilityNotFoundException;
import org.example.pokedexapiinterface.service.IAbilityService;
import org.example.pokedexapiinterface.viewmodel.AbilityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AbilityController.class)
class AbilityControllerTests {

    private static final String ABILITIES_URL = "/api/v1/abilities";
    private static final String ABILITY_1_NAME = "Adaptability";
    private static final String ABILITY_1_DESCRIPTION = "Powers up moves of the same type.";
    private static final int ABILITY_1_GENERATION = 4;
    private static final String ABILITY_2_NAME = "Aerilate";
    private static final String ABILITY_2_DESCRIPTION = "Turns Normal-type moves into Flying-type moves.";
    private static final int ABILITY_2_GENERATION = 6;
    private static final String UNKNOWN_ABILITY_NAME = "Unknown";

    @MockBean
    private IAbilityService abilityService;
    @Autowired
    private MockMvc mockMvc;
    private AbilityDTO abilityDTO;
    private AbilityDTO abilityDTO2;

    @BeforeEach
    void setUp() {
        abilityDTO = new AbilityDTO();
        abilityDTO.setName(ABILITY_1_NAME);
        abilityDTO.setDescription(ABILITY_1_DESCRIPTION);
        abilityDTO.setGeneration(ABILITY_1_GENERATION);
        abilityDTO.add(linkTo(methodOn(AbilityController.class).getAbilityByName(ABILITY_1_NAME.toLowerCase())).withSelfRel());

        abilityDTO2 = new AbilityDTO();
        abilityDTO2.setName(ABILITY_2_NAME);
        abilityDTO2.setDescription(ABILITY_2_DESCRIPTION);
        abilityDTO2.setGeneration(ABILITY_2_GENERATION);
        abilityDTO2.add(linkTo(methodOn(AbilityController.class).getAbilityByName(ABILITY_2_NAME.toLowerCase())).withSelfRel());
    }

    @Test
    void whenGetAbilitiesIsCalled_thenReturnsAbilitiesWithPagination() throws Exception {
        PagedModel<AbilityDTO> pagedModel = PagedModel.of(List.of(abilityDTO, abilityDTO2), new PagedModel.PageMetadata(2, 0, 2));
        when(abilityService.findAll(any(Pageable.class))).thenReturn(pagedModel);

        mockMvc.perform(get(ABILITIES_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page.size").value(2))
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$._embedded.abilities[0].name").value(abilityDTO.getName()))
                .andExpect(jsonPath("$._embedded.abilities[0].description").value(abilityDTO.getDescription()))
                .andExpect(jsonPath("$._embedded.abilities[0].generation").value(abilityDTO.getGeneration()))
                .andExpect(jsonPath("$._embedded.abilities[0]._links.self.href").value(String.format("%s/%s", ABILITIES_URL, ABILITY_1_NAME.toLowerCase())))
                .andExpect(jsonPath("$._embedded.abilities[1].name").value(abilityDTO2.getName()))
                .andExpect(jsonPath("$._embedded.abilities[1].description").value(abilityDTO2.getDescription()))
                .andExpect(jsonPath("$._embedded.abilities[1].generation").value(abilityDTO2.getGeneration()))
                .andExpect(jsonPath("$._embedded.abilities[1]._links.self.href").value(String.format("%s/%s", ABILITIES_URL, ABILITY_2_NAME.toLowerCase())));

        verify(this.abilityService, Mockito.times(1)).findAll(any(Pageable.class));
    }

    @Test
    void whenGetAbilityByNameIsCalled_withExistingName_thenReturnsAbilityDTO() throws Exception {
        when(abilityService.findByName("Adaptability")).thenReturn(Optional.of(abilityDTO));

        mockMvc.perform(get(String.format("%s/%s", ABILITIES_URL, ABILITY_1_NAME))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(abilityDTO.getName()))
                .andExpect(jsonPath("$.description").value(abilityDTO.getDescription()))
                .andExpect(jsonPath("$.generation").value(abilityDTO.getGeneration()))
                .andExpect(jsonPath("$._links.self.href").value(String.format("%s/%s", ABILITIES_URL, ABILITY_1_NAME.toLowerCase())));

        verify(this.abilityService, Mockito.times(1)).findByName(ABILITY_1_NAME);
    }

    @Test
    void whenGetAbilityByNameIsCalled_withNonExistingName_thenReturnsErrorResponseDTO() throws Exception {
        when(abilityService.findByName(UNKNOWN_ABILITY_NAME)).thenThrow(new AbilityNotFoundException("The specified Ability name (unknown) could not be found. To avoid this issue, ensure that the name is spelled correctly and that any spaces in the name are replaced with hyphens. For example, if you're trying to access the Ability 'Armor Tail', you should format it as 'armor-tail' in your request, like so: GET /api/v1/abilities/armor-tail"));

        mockMvc.perform(get(ABILITIES_URL + "/" + UNKNOWN_ABILITY_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Oops! It looks like we couldn't find any results with the search parameters you specified."))
                .andExpect(jsonPath("$.errors").value("The specified Ability name (unknown) could not be found. To avoid this issue, ensure that the name is spelled correctly and that any spaces in the name are replaced with hyphens. For example, if you're trying to access the Ability 'Armor Tail', you should format it as 'armor-tail' in your request, like so: GET /api/v1/abilities/armor-tail"))
                .andExpect(jsonPath("$.path").value(ABILITIES_URL + "/" + UNKNOWN_ABILITY_NAME + "?"));

        verify(abilityService, times(1)).findByName(UNKNOWN_ABILITY_NAME);
    }
}