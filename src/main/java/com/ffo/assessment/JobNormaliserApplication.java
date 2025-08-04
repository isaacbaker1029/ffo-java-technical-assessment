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
        System.out.println("\n--- RUNNING NORMALISATION TEST ---");

        // Let's call your method with a test input
        String result = normalisationService.normalise("Java engineer");

        System.out.println("\nNormalisation result: '" + result + "'");
        System.out.println("--- TEST COMPLETE ---\n");
    }

}