package com.mutantes.mutantes.service;

import com.mutantes.mutantes.dto.StatsResponse;
import com.mutantes.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void testGetStats_WithMutantsAndHumans() {
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(40, response.getCount_mutant_dna());
        assertEquals(100, response.getCount_human_dna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    void testGetStats_NoHumans() {
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(10, response.getCount_mutant_dna());
        assertEquals(0, response.getCount_human_dna());
        // En la implementación actual, si humanos es 0, retorna mutantes como ratio
        // (10.0)
        // Esto es lo que vimos al principio.
        assertEquals(10.0, response.getRatio());
    }

    @Test
    void testGetStats_NoMutants() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCount_mutant_dna());
        assertEquals(100, response.getCount_human_dna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testGetStats_EmptyDatabase() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCount_mutant_dna());
        assertEquals(0, response.getCount_human_dna());
        assertEquals(0.0, response.getRatio());
    }
}
