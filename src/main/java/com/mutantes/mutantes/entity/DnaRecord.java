package com.mutantes.mutantes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dna_records")
@Getter
@Setter
@NoArgsConstructor
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dna_hash", unique = true, nullable = false)  // ¡UNIQUE!
    private String dnaHash;

    @Column(name = "is_mutant", nullable = false)
    private boolean isMutant;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}