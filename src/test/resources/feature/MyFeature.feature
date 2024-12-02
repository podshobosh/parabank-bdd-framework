Feature: This is the test to verify the page titles
As a user I want to verify that home page title matches the expected value

  Scenario: The first scenario
    Given I open the page
    When I navigate to "https://seleniums.com/"
    Then The page title should be "Home - My Digital Notebook"