package com.ffo.assessment.service.impl;

import com.ffo.assessment.service.NormalisationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleNormaliser implements NormalisationService {

    private final List<String> normalisedTitles;

    // Spring injects the value from application.properties into this constructor
    public SimpleNormaliser(@Value("${normalisation.titles}") List<String> normalisedTitles) {
        this.normalisedTitles = normalisedTitles;
    }

    @Override
    public String normalise(String input) {
        System.out.println(input);
        normalisedTitles.forEach(System.out::println);
        return "";
    }
}
