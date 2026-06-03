@UI @DB @API @e2e
Feature: Parabank end-to-end customer banking journey
  This BDD flow covers UI automation, API validation, JDBC database validation,
  Page Object Model usage, Maven execution, and reporting like a real SDET framework.

  Scenario: New customer registers, logs in, opens a checking account, and validates it across UI API and DB
    Given the user on the Parabank registration page
    When user registers with generated valid customer details
    Then registration should succeed
    And customer should exist in the database
    When customer logs out
    And customer logs in with the newly registered credentials
    Then account overview should be displayed
    When customer clicks on Open New Account
    Then customer should be on the open account page
    And customer customer selects checking account dropdown
    And customer selects an existing account to fund the new account
    When customer clicks create account button
    Then the new account should be created successfully
    And a new checking account should be created for the customer in the database
    And the new account should be available from the Parabank accounts API
