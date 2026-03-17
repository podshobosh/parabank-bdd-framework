@UI
Feature: Customer should be able to access and
        interact with all the different account services that are provided

  Background:
    When customer logs in with registered credentials
@smokeAccSer
  Scenario: Customer should be able to create a checking account
    Given customer clicks on Open New Account
    Then customer should be on the open account page
    Then customer customer selects checking account dropdown
    And  customer selects an existing account to fund the new account
    When customer clicks create account button
    Then the new account should be created successfully