Feature: Validating Search Functionality
  Users should be able to search for content/products and get relevant results.

  @UI @smoke @SearchFunc
  Scenario Outline: Verify user is able to enter search terms and able to see valid search results
    Given user is on the home page
    When user enters "<searchTerm>" in the search box
    And user clicks enter
    Then search results for "<searchTerm>" should be displayed

    Examples:
      | searchTerm |
      | Selenium   |
      | Cucumber   |


