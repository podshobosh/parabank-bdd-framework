Feature: Verify the left-side bar on My Notes page
  As a user, I want to verify that the left sidebar on My Notes page displays all the expected items

  Scenario: Verify left sidebar items on My Notes page
    Given I open the browser "chrome"
    When I navigate to "https://seleniums.com/"
    And I click on the "My Notes" menu item
    Then "My Notes" page is loaded
    And the left sidebar contains the following items:
      | Selenium     |
      | Java         |
      | SQL          |
      | Cucumber     |
      | REST Assured |
      | Git          |
      | Cypress      |
      | TestNG       |
      | Jenkins      |
