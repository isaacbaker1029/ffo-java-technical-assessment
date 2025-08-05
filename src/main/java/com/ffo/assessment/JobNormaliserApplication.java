package com.ffo.assessment;

import com.ffo.assessment.service.NormalisationService;
import com.ffo.assessment.service.impl.SimpleNormaliser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobNormaliserApplication implements CommandLineRunner {

    @Autowired
    NormalisationService normalisationService;

    public static void main(String[] args) {
        SpringApplication.run(JobNormaliserApplication.class, args);
    }

    // This run method will be executed automatically after the application starts
    @Override
    public void run(String... args) throws Exception {
        // Using the sample code given
        String jt = "Java engineer";
        String normalisedTitle = normalisationService.normalise(jt);
        System.out.println(normalisedTitle);
        jt = "C# engineer";
        normalisedTitle = normalisationService.normalise(jt);
        System.out.println(normalisedTitle);
        jt = "Chief Accountant";
        normalisedTitle = normalisationService.normalise(jt);
        System.out.println(normalisedTitle);
    }
}