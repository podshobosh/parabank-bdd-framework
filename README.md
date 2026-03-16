# Parabank BDD Automation Framework (UI + DB)

End-to-end automation framework for the Parasoft **Parabank** banking demo application using **Cucumber BDD + Selenium** with optional **DB validation** (HSQLDB via JDBC).

This project is designed to demonstrate real-world banking automation patterns such as authentication, customer registration, account workflows, and database persistence validation using industry-style automation design.

---

## Tech Stack

- **Java 11**
- **Maven**
- **Selenium WebDriver**
- **Cucumber BDD**
- **JUnit 4**
- **Log4j2**
- **HSQLDB JDBC**
- **Docker** (for local Parabank environment)

---

## Project Goal

This framework was built to automate critical banking flows in a realistic retail banking demo environment. The goal is to practice and demonstrate automation skills that apply to real banking systems, including:

- UI automation using Selenium
- BDD-style scenario design with Cucumber
- Reusable Page Object Model design
- Database validation using JDBC
- Stable test data handling
- Local test environment setup with Docker

---

## System Under Test

This project runs against **Parabank locally in Docker** so that UI and database validations use the same environment.

### Local URLs
- **UI:** `http://localhost:8080/parabank`
- **DB:** `jdbc:hsqldb:hsql://localhost:9001/parabank`

---

## Prerequisites

Make sure the following are installed:

- Java 11
- Maven
- Docker Desktop
- Google Chrome
- IntelliJ IDEA (recommended)

You can verify your setup with:

```bash
java -version
mvn -version
docker --version
```
## Running Parabank Locally with Docker

### Pull the Parabank Docker image
```bash
docker pull parasoft/parabank:latest
```

Run the Parabank container
```bash
docker run -d --name parabank \
  -p 8080:8080 \
  -p 9001:9001 \
  parasoft/parabank:latest
```

Verify it is running
```bash
docker ps
nc -vz localhost 9001
```

Open the application:
http://localhost:8080/parabank


Configuration

Edit src/test/resources/config.properties and make sure it contains valid local settings.

Example:

# UI
```bash
home.page.url=http://localhost:8080/parabank/index.htm
browser=chrome
headless=false
default.wait.time=10
page.load.timeout=30
element.visibility.timeout=15
screenshot.dir=screenshots
```

# DB
```bash
DB_URL=jdbc:hsqldb:hsql://localhost:9001/parabank
DB_USER=sa
DB_PASS=
DB_DRIVER=org.hsqldb.jdbcDriver
```

# Optional login test user
```bash
login.username=YOUR_SEEDED_USER
login.password=YOUR_SEEDED_PASSWORD
```

Important: do not include spaces around = in properties.
Use DB_USER=sa, not DB_USER = sa.

Running Tests
Run all tests
```bash
mvn clean test
```
Run the JUnit runner directly
```bash
mvn -Dtest=TestRunner test
Run tests by tag
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@sanity"
mvn test -Dcucumber.filter.tags="@DB"
```

## Test Design Strategy

This framework follows a practical industry-style strategy.

### Smoke Tests

Fast, stable tests that verify the system is up and basic core flows work.

Typical smoke coverage:

- Login with a seeded user
- Accounts Overview page loads
- Logout works

### Sanity Tests

Focused end-to-end flows that validate core business functionality after changes.

Typical sanity coverage:

- Registration
- Login
- Open new account
- Transfer funds
- DB validation for critical persistence checks

---

## Test Data Strategy

### Login Tests

Login smoke tests use a **pre-seeded user** stored in config properties.

### Registration Tests

Registration tests generate a **unique username per run** to avoid collisions and keep tests independent.

Typical pattern:

- static valid defaults for non-unique fields
- runtime-generated username
- database query to validate persistence

This avoids DB cleanup and keeps tests repeatable.

---

## Database Validation

Database validation is performed using JDBC queries against the Parabank Docker HSQLDB instance.

Example validations include:

- registered customer exists in `CUSTOMER`
- account created in `ACCOUNT`
- transaction recorded in `TRANSACTION`


## Project Structure

```text
src
├── main
│   └── java
│       ├── factory/              # WebDriver factory
│       ├── models/               # Data models (e.g. Customer)
│       ├── pages/                # Page Object Model classes
│       └── utils/                # Utilities
│
├── test
│   ├── java
│   │   ├── com/parabank/Runner/              # JUnit runner
│   │   ├── com/parabank/stepdefinitions/     # Step definitions
│   │   └── hooks/                            # Hooks
│   │
│   └── resources
│       ├── features/             # Cucumber feature files
│       ├── config.properties
│       └── log4j2.xml
```

## Page Object Model

This framework uses the **Page Object Model (POM)** for maintainability and readability.

Page objects are responsible for:

- locating elements
- interacting with the UI
- exposing reusable page actions

Step definitions are responsible for:

- scenario flow
- assertions
- test data setup
- DB validation calls

---

## Example Features Covered

- Registering a new customer
- Logging in with valid credentials
- Login page smoke validation
- Customer persistence validation in the database
- Accounts Overview validation
- Transfer funds

---

## Troubleshooting

### 404 on localhost:8080

Use:

```text
http://localhost:8080/parabank
```
### Database Driver Not Found

Make sure:

- `DB_DRIVER=org.hsqldb.jdbcDriver`
- `pom.xml` includes the HSQLDB dependency

### No Features Found

Check that feature files are placed under:

```text
src/test/resources/features/
```

## Undefined Steps

Check:

- package name of step definition class
- runner glue configuration
- exact Gherkin text match

---

## Future Enhancements

- Transfer funds automation
- Database balance verification
- HTML reporting
- CI pipeline with GitHub Actions
- API layer for faster test setup
- Parallel test execution

---

## Author

Automation framework built as part of hands-on Selenium and Cucumber BDD practice using the Parabank banking demo application.


  








