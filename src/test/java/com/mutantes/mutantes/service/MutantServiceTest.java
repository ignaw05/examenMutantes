package com.mutantes.mutantes.service;

import com.mutantes.mutantes.entity.DnaRecord;
import com.mutantes.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @Mock
    private MutantDetector mutantDetector;

    @InjectMocks
    private MutantService mutantService;

    @Test
    void testAnalyzeDna_NewMutant() {
        String[] dna = { "AAAA", "CCCC", "TTTT", "GGGG" };
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_NewHuman() {
        String[] dna = { "ATGC", "CAGT", "TTAT", "AGAC" };
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);

        boolean result = mutantService.analyzeDna(dna);

        assertFalse(result);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_ExistingMutant() {
        String[] dna = { "AAAA", "CCCC", "TTTT", "GGGG" };
        DnaRecord record = new DnaRecord();
        record.setMutant(true);
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(record));

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(mutantDetector, never()).isMutant(any());
        verify(repository, never()).save(any());
    }

    @Test
    void testAnalyzeDna_ExistingHuman() {
        String[] dna = { "ATGC", "CAGT", "TTAT", "AGAC" };
        DnaRecord record = new DnaRecord();
        record.setMutant(false);
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(record));

        boolean result = mutantService.analyzeDna(dna);

        assertFalse(result);
        verify(mutantDetector, never()).isMutant(any());
        verify(repository, never()).save(any());
    }

    @Test
    void testAnalyzeDna_HashCalculation() {
        // Este test verifica indirectamente que el hash se calcula y se usa para buscar
        String[] dna = { "A" };
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);

        mutantService.analyzeDna(dna);

        verify(repository).findByDnaHash(anyString());
    }
}
