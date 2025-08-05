# Job Normaliser

A Spring Boot application to normalize arbitrary job title strings against a configurable list of canonical titles using fuzzy matching.

## Features

- Fuzzy Matching: Uses Jaro–Winkler similarity to match input titles against a list of normalized titles.
- Configurable Threshold: Quality score threshold for fuzzy matches defined in application.properties.
- Defensive Coding: Returns original input if no normalized title meets the threshold.
- Extension-Friendly: Similarity algorithm injected via Spring, allowing custom implementations.

## Prerequisites

- Java 21
- Maven 3.9.9

## Getting Started

Clone the Repository

```
git clone <your-repo-url>
cd job-normaliser
```

## Configuration

Edit src/main/resources/application.properties:
```
# Comma-separated list of job titles
normalisation.titles=Architect,Software engineer,Quantity surveyor,Accountant
# Minimum score for algorithm
normalisation.quality-score-threshold=0.63
# Logging levels
logging.level.com.ffo.assessment.service.impl.SimpleNormaliser=DEBUG
logging.level.com.ffo.assessment.JobNormaliserApplication=DEBUG
```

## Build & Run

Build the project with Maven:
```
mvn clean package
```

Run the Spring Boot application:
```
java -jar target/job-normaliser-1.0-SNAPSHOT.jar
```
You should see normalized titles printed for sample inputs.

## Usage Examples

When the application starts, it normalizes these sample titles:

Java engineer         → Software engineer
C# engineer           → Software engineer
Chief Accountant      → Accountant

To use the service in another Spring component, simply inject NormalisationService:
```
@Autowired
private NormalisationService normalisationService;

String normalized = normalisationService.normalise("Your input here");
```

## Testing

### Unit Tests

Unit tests for SimpleNormaliser cover:

- Happy paths (Java engineer, C# engineer, etc.)
- No-match scenarios (Doctor, Plumber)
- Formatting edge cases (mixed case, whitespace)
- Null and blank inputs
- Tie-breaker behavior with a fake similarity scorer

Run unit tests via Maven:
```
mvn test -Dtest=SimpleNormaliserTest
```

### Integration Tests

Integration tests verify the full Spring context and end-to-end normalization logic.
```
mvn test -Dtest=JobNormaliserIntegrationTest
```

## Logging

Debug and info logs in JobNormaliserApplication and SimpleNormaliser trace scoring and match decisions. Adjust levels in application.properties as needed.

## Limitations & Future Improvements

### Single-word semantic collisions:
- Inputs like Astronaut may score above the threshold against existing titles (e.g., Accountant) due to character-level similarity.
  - Current workaround: A pragmatic guard clause specifically for "astronaut" in SimpleNormaliser.java.
    - Marked as TODO 

Next steps:

- Evolve to a multi-pass pipeline:

  - Pass 1: spelling correction
  - Pass 2: exact token match
  - Pass 3: possible exclusion list
  - Pass 4: fuzzy matching

- Explore semantic approaches (word embeddings or WordNet) to catch subtle mismatches.

