package org.example.pokedexapiinterface.viewmodel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PokemonStatsDTO {

    @NotNull(message = "The \"hp\" field is mandatory.")
    @Min(value = 1, message = "The \"hp\" field must be an integer equal to or greater than 0 and is mandatory.")
    private int hp;

    @NotNull(message = "The \"atk\" field is mandatory.")
    @Min(value = 1, message = "The \"attack\" field must be an integer equal to or greater than 0 and is mandatory.")
    private int atk;

    @NotNull(message = "The \"def\" field is mandatory.")
    @Min(value = 1, message = "The \"defense\" field must be an integer equal to or greater than 0 and is mandatory.")
    private int def;

    @NotNull(message = "The \"spAtk\" field is mandatory.")
    @Min(value = 1, message = "The \"special attack\" field must be an integer equal to or greater than 0 and is mandatory.")
    private int spAtk;

    @NotNull(message = "The \"spDef\" field is mandatory.")
    @Min(value = 1, message = "The \"special defense\" field must be an integer equal to or greater than 0 and is mandatory.")
    private int spDef;

    @NotNull(message = "The \"speed\" field is mandatory.")
    @Min(value = 1, message = "The \"speed\" field must be an integer equal to or greater than 0 and is mandatory.")
    private int speed;

    @NotNull(message = "The \"total\" field is mandatory.")
    @Min(value = 1, message = "The \"total\" field must be an integer equal to or greater than 0 and is mandatory.")
    private int total;
}
