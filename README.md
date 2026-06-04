# Digital Banking Automation Framework

A Java-based automation framework for a simulated digital banking platform. The project demonstrates UI, API, database integration checks, BDD test design, Maven execution, and CI-ready reporting using a maintainable layered structure.

## Tech stack

- Java 11
- Selenium WebDriver
- Cucumber BDD
- TestNG
- REST-Assured
- JDBC / SQL for targeted data consistency checks
- Maven
- Jenkins Pipeline
- Docker-hosted banking simulation test environment

## What this framework demonstrates

- Page Object Model for reusable UI automation
- ThreadLocal WebDriver factory for future parallel execution support
- Config reader with Maven/system property overrides
- Config-driven test users and generated data helpers
- Scenario context for sharing runtime data between Cucumber steps
- REST-Assured API client layer for service-level validation
- Targeted JDBC repository layer for integration/data consistency checks
- Cucumber, Surefire, and JUnit XML reporting
- Jenkinsfile for CI execution

## Test strategy

The framework separates validation responsibilities instead of using every tool in every test.

### UI end-to-end flow

Cucumber + Selenium validates the user workflow:

1. Authenticate with a configured test customer
2. Open a new checking account from the customer session
3. Confirm the new account from the UI
4. Verify the created account through the public Parabank API

Feature file:

```text
src/test/resources/features/e2e_banking_journey.feature
```

### API tests

REST-Assured validates service endpoints directly:

```text
src/test/java/com/parabank/api/ParabankApiTest.java
src/test/java/api/ParabankApiClient.java
```

### Database integration checks

JDBC is used as a targeted integration validation layer, not as a replacement for UI or API assertions. These tests verify that service-visible account data is consistent with persisted database records in the local Docker test environment.

```text
src/test/java/com/parabank/database/BankingDatabaseValidationTest.java
src/test/java/db/repository/
src/test/resources/sql/
```

This mirrors a common automation pattern: UI tests validate user behavior, API tests validate service contracts, and DB checks are reserved for controlled integration/data consistency scenarios where direct database access is available.

## Project structure

```text
src/main/java/factory/DriverFactory.java       ThreadLocal WebDriver management
src/main/java/pages/                           Page Object Model classes
src/main/java/models/Customer.java             Customer test data model
src/main/java/utils/ConfigReader.java          Configuration and system property reader
src/main/java/utils/ScenarioContext.java       Scenario-level runtime state
src/main/java/utils/TestDataFactory.java       Test data generation
src/test/java/api/ParabankApiClient.java       REST-Assured client wrapper
src/test/java/com/parabank/api/                API tests
src/test/java/com/parabank/database/           Database integration/data consistency tests
src/test/java/com/parabank/Runner/             Cucumber TestNG runner
src/test/java/com/parabank/stepdefinitions/    Cucumber step definitions
src/test/java/db/core/                         JDBC template, SQL loader, row mapper
src/test/java/db/model/                        Database record models
src/test/java/db/repository/                   Repository classes for SQL queries
src/test/resources/features/                   Cucumber feature files
src/test/resources/sql/                        Externalized SQL queries
```

## Prerequisites

Parabank should be running locally through Docker:

```bash
docker start parabank
curl -I http://localhost:8080/parabank/index.htm
```

Expected endpoints:

```text
UI:  http://localhost:8080/parabank
API: http://localhost:8080/parabank/services/bank
DB:  jdbc:hsqldb:hsql://localhost:9001/parabank
```

## Run tests

Headless Chrome:

```bash
mvn clean test -Dheadless=true -Dbrowser=chrome
```

Visible Chrome:

```bash
mvn clean test -Dheadless=false -Dbrowser=chrome
```

Run only Cucumber E2E scenarios:

```bash
mvn clean test -Dcucumber.filter.tags='@e2e' -Dheadless=true
```

Run by TestNG groups:

```bash
mvn clean test -Dgroups=api
mvn clean test -Dgroups=db
```

## Reports

After execution:

```text
target/cucumber-reports.html
target/cucumber.json
target/cucumber.xml
target/surefire-reports/
logs/
```
