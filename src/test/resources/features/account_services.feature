@UI @account
Feature: Account services
  Authenticated customers should be able to manage account services from the Parabank portal.

  Background:
    When customer logs in with registered credentials

  Scenario: Customer opens a checking account
    When the customer navigates to Open New Account
    Then the Open New Account page should be displayed
    And the customer selects Checking as the account type
    And the customer selects an existing funding account
    And the customer submits the open account request
    Then the new checking account should be displayed in the UI
