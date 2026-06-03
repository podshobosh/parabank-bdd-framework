@UI @API @e2e
Feature: Customer account opening journey
  Authenticated customers should be able to open a checking account
  and retrieve the newly created account through the account service.

  Scenario: Authenticated customer opens a new checking account
    When customer logs in with registered credentials
    Then account overview should be displayed
    When the customer navigates to Open New Account
    And the customer selects Checking as the account type
    And the customer selects an existing funding account
    When the customer submits the open account request
    Then the new checking account should be displayed in the UI
    And the new checking account should be retrievable from the accounts API
