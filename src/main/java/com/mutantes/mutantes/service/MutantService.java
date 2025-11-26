package com.mutantes.mutantes.service;

import com.mutantes.mutantes.entity.DnaRecord;
import com.mutantes.mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector mutantDetector;

    public boolean analyzeDna(String[] dna) {
        // Calcular hash del DNA
        String dnaHash = this.calculateDnaHash(dna);

        // Verificar si ya existe en BD
        Optional<DnaRecord> existing = repository.findByDnaHash(dnaHash);
        if (existing.isPresent()) {
            return existing.get().isMutant(); // Retornar resultado cacheado
        }

        // Analizar DNA
        boolean isMutant = mutantDetector.isMutant(dna);

        // Guardar resultado
        DnaRecord record = new DnaRecord();
        record.setDnaHash(dnaHash);
        record.setMutant(isMutant);
        record.setCreatedAt(LocalDateTime.now());
        repository.save(record);

        return isMutant;
    }

    private String calculateDnaHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join("", dna);
            byte[] hashBytes = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            // Convertir a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new com.mutantes.mutantes.exception.DnaHashCalculationException("Error calculando hash", e);
        }

    }
}
