package com.mutantes.mutantes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    // Mutante - Horizontal + Vertical (1.0 pts)
    @Test
    public void testMutantWithHorizontalAndVertical() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    // Mutante - Diagonal (1.0 pts)
    @Test
    public void testMutantWithDiagonalSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    // Humano - Sin secuencias (1.0 pts)
    @Test
    public void testHumanWithNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    // Humano - 1 secuencia (1.0 pts)
    @Test
    public void testHumanWithOnlyOneSequence() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    // Validación - Matriz no cuadrada (1.0 pts)
    @Test
    public void testInvalidDnaNonSquare() {
        String[] dna = {
                "ATGC",
                "CAG",
                "TTAT"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    // Validación - Caracteres inválidos (1.0 pts)
    @Test
    public void testInvalidDnaCharacters() {
        String[] dna = {
                "ATGX",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    // Validación - Null (1.0 pts)
    @Test
    public void testNullDnaArray() {
        assertFalse(mutantDetector.isMutant(null));
    }

    // Validación - Empty (1.0 pts)
    @Test
    public void testEmptyDnaArray() {
        String[] dna = {};
        assertFalse(mutantDetector.isMutant(dna));
    }
    

    @Test
    void testMutantVerticalOnly() {
        String[] dna = {
                "ATGC",
                "ATGC",
                "ATGC",
                "ATGC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalAscending() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TTAAGT",
                "AGATGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testMutantMixed() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testNxM_Matrix() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testNotSquareMatrix() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testNullRow() {
        String[] dna = {
                "ATGC",
                null,
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testShortRow() {
        String[] dna = {
                "ATGC",
                "CAG",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testLargeMatrixPerformance() {
        int n = 100;
        String[] dna = new String[n];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append("A");
        String rowA = sb.toString();

        sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append("T");
        String rowT = sb.toString();

        for (int i = 0; i < n; i++) {
            dna[i] = (i % 2 == 0) ? rowA : rowT;
        }
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    void testMutantCross() {
        String[] dnaValidCross = {
                "ATGC",
                "ATGC",
                "ATGC",
                "ATGC"
        };
        assertTrue(mutantDetector.isMutant(dnaValidCross));
    }
}
