package com.ffo.assessment.service.impl;

import com.ffo.assessment.service.NormalisationService;
import org.springframework.stereotype.Service;

@Service
public class SimpleNormaliser implements NormalisationService {
    @Override
    public String normalise(String input) {
        return "";
    }
}
