Feature: Open the Application
  As a user, I want to open the application in different browsers and verify the homepage is loaded.

  Scenario Outline: Open application in specified browser
    Given I open the browser "<browserType>"
    When I navigate to "<url>"
    Then The page title should be "<expectedTitle>"

    Examples:
      | browserType | url                       | expectedTitle               |
      | chrome      | https://seleniums.com/    | Home - My Digital Notebook  |
      | firefox     | https://seleniums.com/    | Home - My Digital Notebook  |
      | edge        | https://seleniums.com/    | Home - My Digital Notebook  |
