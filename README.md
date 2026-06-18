[![Java CI with Gradle](https://github.com/Ermolz/java-practice07/actions/workflows/ci.yml/badge.svg)](https://github.com/Ermolz/java-practice07/actions/workflows/ci.yml)
# Bank Transfer Service

This project implements a robust bank transfer service demonstrating domain-driven design, layered architecture, and comprehensive testing strategies.

## Features & Technologies
- **Java 17**
- **Gradle** for dependency management and building
- **Lombok** for boilerplate reduction (getters, constructors)
- **JUnit 5** for unit testing
- **Mockito** for mocking dependencies (`@Mock`, `@InjectMocks`, `when().thenReturn()`, `verify()`, etc.)
- **AssertJ** for fluent assertions, including `SoftAssertions` and advanced List assertions (`hasSize`, `extracting`, `contains`, `filteredOn`)
- **PIT Mutation Testing** to ensure the strength of the test suite and verify edge cases

## Project Structure (Layered Architecture)
The codebase is structured logically to separate concerns:
* `bank.domain`: Contains business models (`Account`, `Transfer`). The `Account` entity protects its own invariants (e.g., throwing exceptions on insufficient funds).
* `bank.repository`: Contains data access interfaces (`AccountRepository`).
* `bank.service`: Contains the core business logic (`TransferService`, `CommissionService`, `CommissionCalculator`, `AuditService`).
* `bank.exception`: Custom domain exceptions (`InvalidAmountException`, `AccountNotFoundException`, `InsufficientFundsException`).

## Running the Application

### 1. Run Unit Tests
To execute the test suite (JUnit 5 + Mockito + AssertJ):
```bash
./gradlew test
```

### 2. Run Mutation Tests
To execute PIT Mutation Testing and generate a mutation coverage report:
```bash
./gradlew pitest
```
The HTML report will be generated at `build/reports/pitest/`.

## Checklist References
Throughout the codebase, you will find comments mapping specific code segments to implementation requirements (e.g., `// 1. Class Structure`, `// 3. Minimum 3 business scenarios`, `// 8. PIT mutation testing`). These are left intentionally to serve as a walkthrough of the fulfilled requirements.
