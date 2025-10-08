Feature: Verifying HomePage Main Functionalities
  As a user, I want to verify that the HomePage functionalities work as expected, including navigation, search, and theme toggle features.

  @UI @Regression @smoke
  Scenario: Verify HomePage URL and Title
    Then The page should have the title "Home - My Digital Notebook"
    And The page URL should be "https://seleniums.com/"

  @UI
  Scenario: Verify the top page search functionality
    Then the top search button should be present
    When I click on the search button
    Then the search box field should expand
    When I click on the x button the search box field should collapse

  @UI
  Scenario: Verify the main menu contains the correct options, they are clickable, and navigate to the expected pages.
    Given the main menu contains the following options:
      | Home                |
      | My Notes            |
      | Interview Questions |
      | Blog                |
      | Forums              |
    Then all menu options should be displayed and clickable
    When I click on each menu option
      | Home                | https://www.seleniums.com/                                | Home - My Digital Notebook                |
      | My Notes            | https://www.seleniums.com/automation/notes/               | My Notes - My Digital Notebook            |
      | Interview Questions | https://www.seleniums.com/automation/interview-questions/ | Interview Questions - My Digital Notebook |
      | Blog                | https://www.seleniums.com/blog/                           | Blog - My Digital Notebook                |
      | Forums              | https://www.seleniums.com/forums/                         | Forums Archive - My Digital Notebook      |

  @UI @smoke1
  Scenario Outline: Verify the website logo functionality
    Given user is on the home page
    Then the website logo should be present on the top left of the page
    When the user navigates to "<menuOption>" page
    And user clicks on the website logo
    Then user should be redirected to the HomePage

    Examples:
      | menuOption          |
      | My Notes            |
      | Interview Questions |
      | Blog                |
      | Forums              |

#  Scenario: Verify the Register and Login links functionality
#    Then the Register link should be present on the top right
#    And the Login link should be present on the top right
#    When I click on the Register link
#    Then I should be redirected to the Register page
#    When I click on the Login link
#    Then I should be redirected to the Login page
#
#  Scenario: Verify the About me link functionality
#    Then the Forums link should be present on the top left
#    When I click on the Forums link
#    Then I should be redirected to the Forums page
