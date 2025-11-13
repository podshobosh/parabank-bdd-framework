@Testcase002 @db @jdbc
Feature: Simple JDBC verification without UI
  As an SDET
  I want to validate basic JDBC operations via DatabaseUtils
  So that I can confirm DB connectivity and CRUD basics work in BDD style

  Background:
    Given the database is initialized and cleaned

  Scenario: Insert a customer and verify via JDBC only
    When I insert a customer with details
      | username | password    | first_name | last_name | email                | ssn         | address     | city       | state | zip   | phone       |
      | testuser | password123 | Test       | User      | testuser@example.com | 123-45-6789 | 123 Main St | Metropolis | NY    | 12345 | +1-555-0100 |
    Then the customer "testuser" should exist in the database
    And fetching the id for username "testuser" should return a valid id
    And I print the full customer record for username "testuser"