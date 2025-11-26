package com.mutantes.mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {
    @Schema(description = "Cantidad de ADNs mutantes detectados", example = "40")
    private long count_mutant_dna;

    @Schema(description = "Cantidad de ADNs humanos detectados", example = "100")
    private long count_human_dna;

    @Schema(description = "Ratio de mutantes (mutantes / humanos)", example = "0.4")
    private double ratio;
}
