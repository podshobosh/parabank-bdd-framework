# Parabank End-to-End SDET Automation Framework

This is a Freedom Bank style QA automation framework for the local Docker Parabank app.

## Tech stack covered

- Java OOP: POJO model, utility classes, factory pattern, reusable methods
- Selenium WebDriver: UI automation with Page Object Model and PageFactory
- Cucumber BDD: feature files, step definitions, hooks, runner, tags
- TestNG: Maven executes Cucumber and REST tests through TestNG provider
- REST-Assured: API validation against Parabank service endpoints
- JDBC / SQL: backend validation with PreparedStatement, repository layer, external SQL files, row mappers, and DB-to-API comparison against Docker HSQLDB
- Maven: dependency management and test lifecycle
- Reports: Cucumber HTML/JSON/JUnit XML and Surefire/TestNG reports
- Jenkins-ready: Jenkinsfile runs the Maven suite and publishes reports

## Prerequisites

Parabank Docker container should be running:

```bash
docker start parabank
curl -I http://localhost:8080/parabank/index.htm
```

Expected exposed ports:

- UI/API: http://localhost:8080/parabank
- DB: jdbc:hsqldb:hsql://localhost:9001/parabank

## Run tests

Headless Chrome, good for CI/Jenkins:

```bash
mvn clean test -Dheadless=true -Dbrowser=chrome
```

Visible Chrome, good for learning/debugging:

```bash
mvn clean test -Dheadless=false -Dbrowser=chrome
```

Run only Cucumber tags:

```bash
mvn clean test -Dcucumber.filter.tags='@e2e' -Dheadless=true
```

## Main test flow

Feature file:

```text
src/test/resources/features/e2e_banking_journey.feature
```

Scenario covers:

1. Register a new customer through UI
2. Validate customer exists in DB using JDBC
3. Logout and login with the new customer
4. Open a new checking account through UI
5. Validate account exists and ownership is correct using repository-backed JDBC assertions
6. Validate account through REST-Assured API
7. Compare API account response with database account record

## Important framework layers

```text
src/main/java/factory/DriverFactory.java       Singleton-style ThreadLocal WebDriver manager
src/main/java/pages/                           Page Object Model classes
src/main/java/models/Customer.java             POJO for customer test data
src/main/java/utils/ConfigReader.java          Config + system property reader
src/main/java/utils/DatabaseUtils.java         JDBC connection manager only
src/test/java/db/core/                         Professional JDBC template, SQL loader, row mapper
src/test/java/db/model/                        DB result models for customer/account records
src/test/java/db/repository/                   Repository classes that hide SQL from tests
src/test/java/db/assertions/                   Reusable banking DB assertion layer
src/test/resources/sql/                        External SQL used by repository methods
src/test/java/com/parabank/database/           Backend database validation tests
src/main/java/utils/ScenarioContext.java       Scenario-level shared test state
src/test/java/com/parabank/stepdefinitions/    Cucumber step definitions
src/test/java/com/parabank/api/                TestNG REST-Assured API tests
src/test/java/api/ParabankApiClient.java       Reusable API client layer
src/main/java/hooks/                           Cucumber hooks for browser/DB setup
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
