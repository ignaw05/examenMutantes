package com.mutantes.mutantes.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false; // ADN inválido
        }

        final int n = dna.length;

        // Validar y convertir a matriz de caracteres
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            String row = dna[i];
            if (row == null || row.length() != n) {
                return false; // No es matriz NxN
            }
            matrix[i] = row.toCharArray();
            for (char c : matrix[i]) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    return false; // Carácter inválido
                }
            }
        }

        int sequenceCount = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1)
                            return true;
                    }
                }

                // Vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1)
                            return true;
                    }
                }

                // Diagonal Descendente (↘)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1)
                            return true;
                    }
                }

                // Diagonal Ascendente (↗)
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1)
                            return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }

    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }
}
