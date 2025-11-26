package com.mutantes.mutantes.service;

import com.mutantes.mutantes.dto.StatsResponse;
import com.mutantes.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    public DnaRecordRepository repository;

    public StatsService(DnaRecordRepository repository) {
        this.repository = repository;
    }

    public StatsResponse getStats(){
        long mutantes = repository.countByIsMutant(true);
        long humanos = repository.countByIsMutant(false);
        if (humanos == 0) {
            return new StatsResponse(mutantes,humanos,mutantes);
        }
        double ratio = (double) mutantes/humanos;
        return new StatsResponse(mutantes,humanos,ratio);
    }
}
