package com.ffo.assessment.service.impl;

import com.ffo.assessment.service.NormalisationService;
import org.apache.commons.text.similarity.SimilarityScore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SimpleNormaliserTest {

    @Autowired
    private NormalisationService normaliser;

    @Value("${normalisation.titles}")
    private List<String> normalisedTitlesFromConfig;

    @Value("${normalisation.quality-score-threshold}")
    private double qualityScoreFromConfig;

    // Happy Path
    @ParameterizedTest(name = "Input: ''{0}'', Expected: ''{1}''")
    @CsvSource({
            "Java engineer,    Software engineer",
            "C# engineer,      Software engineer",
            "Accountant,       Accountant",
            "Chief Accountant, Accountant"
    })
    void normalise_shouldReturnCorrectTitle_forValidInputs(String input, String expected) {
        String result = normaliser.normalise(input);
        assertEquals(expected, result);
    }

    // No Matches
    @ParameterizedTest(name = "Input: ''{0}''")
    @ValueSource(strings = {"Doctor", "Plumber"})
    void normalise_shouldReturnOriginalInputForNoMatch(String input) {
        assertEquals(input, normaliser.normalise(input));
    }

    // Edge cases (mixed cases or whitespace)
    @ParameterizedTest(name = "Input: ''{0}'' -> Expected: ''{1}''")
    @CsvSource({
            "jAvA eNgInEeR,      Software engineer",
            "'  Java engineer  ',  Software engineer",
            "'Chief Accountant\t', Accountant"
    })
    void normalise_shouldHandleFormattingEdgeCases(String input, String expected) {
        assertEquals(expected, normaliser.normalise(input));
    }

    // NULL or Blank String
    @ParameterizedTest(name = "Input: ''{0}''")
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void normalise_shouldHandleNullAndBlankInputs(String input) {
        String result = normaliser.normalise(input);
        assertEquals(input, result);
    }

    // Tie-Breaker
    @Test
    void normalise_shouldReturnFirstBestMatch_whenScoresAreTied() {
        // Arrange: Create our own Normaliser instance with the fake algorithm
        NormalisationService normaliserWithFakeScore = new SimpleNormaliser(
                normalisedTitlesFromConfig,
                qualityScoreFromConfig,
                new FakeTieBreakingScore() // Pass in our fake class
        );

        String input = "Lorem ipsum dolor sit amet";
        // Since "Architect" appears first in our config list, it should win the tie.
        String expected = "Architect";

        String result = normaliserWithFakeScore.normalise(input);
        assertEquals(expected, result);
    }

    // This allows us to make use of our new parameterized testing and implement a fake similarity score when we have competing scores for an input
    // Should return the first
    private static class FakeTieBreakingScore implements SimilarityScore<Double> {
        @Override
        public Double apply(CharSequence left, CharSequence right) {
            String normalisedTitle = right.toString().toLowerCase();
            return switch (normalisedTitle) {
                case "architect" -> 0.9;
                case "software engineer" -> 0.8;
                case "accountant" -> 0.9; // The tie
                default -> 0.7;
            };
        }
    }

}