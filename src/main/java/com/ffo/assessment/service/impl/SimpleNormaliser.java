package com.ffo.assessment.service.impl;

import com.ffo.assessment.service.NormalisationService;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.SimilarityScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleNormaliser implements NormalisationService {

    private static final Logger logger = LoggerFactory.getLogger(SimpleNormaliser.class);

    private final List<String> normalisedTitles;
    private final SimilarityScore<Double> similarityAlgorithm; // Changed to the generic interface so we can use our own fake score implemenation for testing
    private final double minimumQualityScore;

    // Spring injects the value from application.properties into this constructor
    public SimpleNormaliser(
            @Value("${normalisation.titles}") List<String> normalisedTitles,
            @Value("${normalisation.quality-score-threshold}") double minimumQualityScore,
            SimilarityScore<Double> similarityAlgorithm) {
        this.normalisedTitles = normalisedTitles;
        this.minimumQualityScore = minimumQualityScore;
        this.similarityAlgorithm = similarityAlgorithm;
    }

    @Override
    public String normalise(String inputTitle) {
        // 1. Check for null/blank inputs
        if (inputTitle == null || inputTitle.trim().isEmpty()) {
            logger.debug("String passed is empty or NULL, returning early");
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

            logger.debug("Name '{}' - Score: {}", normalisedTitle, String.format("%.2f", currentScore));

            // 4. Check if this is the best score so far
            if (currentScore > highestScore) {
                highestScore = currentScore;
                bestMatch = normalisedTitle;
            }
        }

        // 5. Only return the match if it's good enough
        if (highestScore >= this.minimumQualityScore) {
            logger.info("Found a quality match for input ''{}'': Normalised to ''{}'' with score {}",
                    inputTitle,
                    bestMatch,
                    String.format("%.2f", highestScore));
            return bestMatch;
        }

        // Otherwise, return the original input
        logger.info("No quality match found for input ''{}''. Highest score was {}",
                inputTitle,
                String.format("%.2f", highestScore));
        return inputTitle;
    }
}