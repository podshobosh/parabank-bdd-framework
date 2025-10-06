Feature: Verify Login Page Functionality
  @UI @TestLogin
  Scenario Outline: Verify login with multiple credentials
    Given user is one the login page
    When user enters "<username>" and "<password>"
    And clicks Login button
    Then user should see "<message>"
    Examples:
      | username     | password     | message                               |
      | invalid_user | invalid_pass | ERROR: Incorrect Username or Password |
