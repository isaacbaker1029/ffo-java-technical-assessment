package com.ffo.assessment.service.impl;

import com.ffo.assessment.service.NormalisationService;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleNormaliser implements NormalisationService {

    private final List<String> normalisedTitles;
    private final JaroWinklerSimilarity similarityAlgorithm;
    private final double minimumQualityScore; // This field is needed

    // Spring injects the value from application.properties into this constructor
    public SimpleNormaliser(
            @Value("${normalisation.titles}") List<String> normalisedTitles,
            @Value("${normalisation.quality-score-threshold}") double minimumQualityScore
    ) {
        this.normalisedTitles = normalisedTitles;
        this.similarityAlgorithm = new JaroWinklerSimilarity();
        this.minimumQualityScore = minimumQualityScore; // This assignment is needed
    }

    @Override
    public String normalise(String inputTitle) {
        // 1. Check for null/blank inputs
        if (inputTitle == null || inputTitle.trim().isEmpty()) {
            return inputTitle;
        }

        String bestMatch = null;
        double highestScore = -1.0;

        // Clean the input once for efficiency
        String cleanedInput = inputTitle.trim().toLowerCase();

        // 2. Loop through normalised titles
        for (String normalisedTitle : this.normalisedTitles) {
            // 3. Calculate the similarity score
            double currentScore = similarityAlgorithm.apply(cleanedInput, normalisedTitle.toLowerCase());

            // 4. Check if this is the best score so far
            if (currentScore > highestScore) {
                highestScore = currentScore;
                bestMatch = normalisedTitle;
            }
        }

        // 5. Only return the match if it's good enough
        if (highestScore >= this.minimumQualityScore) {
            return bestMatch;
        }

        // Otherwise, return the original input
        return inputTitle;
    }
}