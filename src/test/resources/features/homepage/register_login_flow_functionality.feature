@UI @DB
Feature: Register then login

  Background:
    Given user is on the Home Page

  Scenario Outline: Register a new user
    When user navigates to register page
    And user provides registration information:
      | firstName | <firstName> |
      | lastName  | <lastName>  |
      | address   | <address>   |
      | city      | <city>      |
      | state     | <state>     |
      | zipCode   | <zipCode>   |
      | phone     | <phone>     |
      | ssn       | <ssn>       |
      | username  | <username>  |
      | password  | <password>  |
    And user clicks register button
    Then user should see registration success message


    Examples:
      | firstName | lastName | address     | city     | state | zipCode | phone        | ssn         | username      | password  |
      | John      | Doe      | 123 Main St | New York | NY    | 10001   | 555-123-4567 | 123-45-6789 | user${RANDOM} | P@ssw0rd! |


  @ui @shadow-db
  Scenario: Register via UI, mirror to H2, and assert DB record exists
    When user navigates to register page
    And user provides registration information:
      | firstName | John          |
      | lastName  | Doe           |
      | address   | 123 Main St   |
      | city      | New York      |
      | state     | NY            |
      | zipCode   | 12345         |
      | phone     | 555-123-4567  |
      | ssn       | 123-45-6789   |
      | username  | user${RANDOM} |
      | password  | P@ssw0rd!     |
    And user clicks register button
    Then user should see registration success message
    And I mirror the user into DB and verify it exists

