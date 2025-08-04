package com.ffo.assessment.service.impl;

import com.ffo.assessment.service.NormalisationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SimpleNormaliserTest {

    @Autowired
    private NormalisationService normaliser;

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

}