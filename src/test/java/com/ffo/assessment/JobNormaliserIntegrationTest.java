package com.ffo.assessment;

import com.ffo.assessment.service.NormalisationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobNormaliserIntegrationTest {

    @Autowired
    private NormalisationService normalisationService;

    @DisplayName("Context loads correctly")
    @Test
    void contextLoads() {
        assertNotNull(normalisationService, "NormalisationService should be injected");
    }

    @DisplayName("Match should be found with normalisation working correctly")
    @Test
    void normalize_sampleTitles_shouldMatchConfigured() {
        assertEquals(
            "Software engineer",
            normalisationService.normalise("Java engineer"),
            "Java engineer → Software engineer"
        );
        assertEquals(
            "Accountant",
            normalisationService.normalise("Chief Accountant"),
            "Chief Accountant → Accountant"
        );
    }

    @DisplayName("Hard-coded guard: ‘Astronaut’ should return itself")
    @Test
    void normalize_astronautReturnsItself_showsNeedForImprovement() {
        String input = "Astronaut";
        String output = normalisationService.normalise(input);
        assertEquals(input, output, "Pragmatic exception: 'Astronaut' is hard-coded to bypass fuzzy matching");
    }
}