package com.mutantes.mutantes.controller;

import com.mutantes.mutantes.dto.DnaRequest;
import com.mutantes.mutantes.service.MutantService;
import com.mutantes.mutantes.service.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;


    @Test
    public void testMutantEndpoint_ReturnOk() throws Exception {
        when(mutantService.analyzeDna(any())).thenReturn(true);

        String dnaJson = """
                {
                    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
                }
                """;

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dnaJson))
                .andExpect(status().isOk());
    }


    @Test
    public void testHumanEndpoint_ReturnForbidden() throws Exception {
        when(mutantService.analyzeDna(any())).thenReturn(false);

        String dnaJson = """
                {
                    "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
                }
                """;

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dnaJson))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testInvalidDna_ReturnBadRequest() throws Exception {
        String dnaJson = """
                {
                    "dna": ["ATGX","CAGT"]
                }
                """;

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dnaJson))
                .andExpect(status().isBadRequest());
    }

    // Test stats endpoint (1.0 pts)
    @Test
    public void testStatsEndpoint_ReturnOk() throws Exception {
        com.mutantes.mutantes.dto.StatsResponse stats = new com.mutantes.mutantes.dto.StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(stats);

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").exists())
                .andExpect(jsonPath("$.count_human_dna").exists())
                .andExpect(jsonPath("$.ratio").exists());
    }
}
