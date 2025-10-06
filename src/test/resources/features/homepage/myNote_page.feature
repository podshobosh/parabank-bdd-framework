Feature: Verifying My Notes Tab fields


  @UI @TestCase1
  Scenario: Verify My Notes Tab fields
    Given User click on My Notes Tab
    And Validate that topic of fields are present at the left side of the page
    When User clicks on Selenium topic
    Then Validate Selenium title is present

  @UI @Testcase002
  Scenario Outline: Validate each section title after clicking sidebar links
    Given User click on My Notes Tab
    When User clicks the "<section>" link from the sidebar
    Then User should see title "<section>"

    Examples:
      | section      |
      | Selenium     |
      | Java         |
      | SQL          |
      | Cucumber     |
      | REST Assured |
      | Git          |
      | Cypress      |
      | TestNG       |
      | Jenkins      |



