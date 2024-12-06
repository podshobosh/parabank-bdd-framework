Feature: Launching the application
@regression
  Scenario: Launch the browser and navigate to the homepage
    Given I launch the application
    Then I should see the homepage loaded with the title "Home - My Digital Notebook"
    And I close the browser
