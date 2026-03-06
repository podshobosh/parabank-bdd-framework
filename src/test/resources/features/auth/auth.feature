@UI @DB
Feature: Authentication (Register + Login)
  User should be able to register an account
  and then be able to log into that account

  @sanityReg
  Scenario Outline: Registering a new customer persists the customer in the database
    Given the user on the Parabank registration page
    When user registers with the valid customer details
      | firstName | <firstName> |
      | lastName  | <lastName>  |
      | address   | <address>   |
      | city      | <city>      |
      | state     | <state>     |
      | zip       | <zip>       |
      | phone     | <phone>     |
      | ssn       | <ssn>       |
      | password  | <password>  |
    Then registration should succeed
    And customer should exist in the database

    Examples:
      | firstName | lastName | address     | city     | state | zip   | phone      | ssn       | password  |
      | Test      | User     | 123 Main St | Leesburg | VA    | 20175 | 7035550123 | 123456789 | Test@1234 |

  @UI @smokeLog
  Scenario: A registered user can log in successfully
    Given the login panel is visible
    When customer logs in with registered credentials
    Then account overview should be displayed