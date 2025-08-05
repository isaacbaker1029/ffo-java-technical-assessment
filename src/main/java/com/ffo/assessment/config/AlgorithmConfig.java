package com.ffo.assessment.config;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.SimilarityScore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlgorithmConfig {
    @Bean // This method provides the default bean for the whole application
    public SimilarityScore<Double> similarityScore() {
        return new JaroWinklerSimilarity();
    }
}