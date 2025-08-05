package com.ffo.assessment;

import com.ffo.assessment.service.NormalisationService;
import com.ffo.assessment.service.impl.SimpleNormaliser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobNormaliserApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(JobNormaliserApplication.class);

    @Autowired
    NormalisationService normalisationService;

    public static void main(String[] args) {
        SpringApplication.run(JobNormaliserApplication.class, args);
    }

    // This run method will be executed automatically after the application starts
    @Override
    public void run(String... args) throws Exception {
        // Using the sample code given
        logger.info("--- Running Normalisation Service Demonstration ---");
        logger.info("This demonstration executes the sample code provided in the technical specification.");

        String jt1 = "Java engineer";
        String normalisedTitle1 = normalisationService.normalise(jt1);
        logger.info("Input: '{}' -> Output: '{}'", jt1, normalisedTitle1);

        String jt2 = "C# engineer";
        String normalisedTitle2 = normalisationService.normalise(jt2);
        logger.info("Input: '{}' -> Output: '{}'", jt2, normalisedTitle2);

        String jt3 = "Chief Accountant";
        String normalisedTitle3 = normalisationService.normalise(jt3);
        logger.info("Input: '{}' -> Output: '{}'", jt3, normalisedTitle3);

        logger.info("--- Demonstration Complete ---");
    }
}