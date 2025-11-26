package com.mutantes.mutantes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.mutantes.mutantes.dto.DnaRequest;
import com.mutantes.mutantes.dto.StatsResponse;
import com.mutantes.mutantes.service.MutantService;
import com.mutantes.mutantes.service.StatsService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "Endpoints para detección de mutantes y estadísticas")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @Operation(summary = "Detectar si un humano es mutante", description = "Analiza la secuencia de ADN enviada y determina si es mutante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante"),
            @ApiResponse(responseCode = "403", description = "No es Mutante (Es Humano)"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido (formato incorrecto)")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Validated @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());
        return isMutant
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Obtener estadísticas", description = "Devuelve estadísticas sobre las verificaciones de ADN realizadas.")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente")
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}
