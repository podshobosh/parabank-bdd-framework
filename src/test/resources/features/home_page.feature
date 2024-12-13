Feature: Verifying HomePage Main Functionalities
  As a user, I want to verify that the HomePage functionalities work as expected, including navigation, search, and theme toggle features.

  Background:
    Given I am on the HomePage
    Then I should see the homepage loaded with the title "Home - My Digital Notebook"

  @regression
  Scenario: Verify the search button functionality
    Then the search button should be present
    When I click on the search button
    Then the search box field should expand
    When I click on the x button the search box field should collapse

#  Scenario: Verify the light/dark mode toggle functionality
#    Then the light/dark mode toggle button should be present
#    When I click on the light/dark mode toggle button
#    Then the theme color should change
#
#  Scenario: Verify the main menu contains 5 options, they are clickable, and navigate to relevant pages
#    Then the main menu should contain 5 options: "Home", "My Notes", "Interview Questions", "Blog", "Forums"
#    And each menu option should be clickable
#    When I click on each menu option, it should take me to the relevant page with the correct title and URL:
#      | Menu Option         | Relevant Page URL    | Page Title                                |
#      | Home                | /                    | Home - My Digital Notebook                |
#      | My Notes            | /my-notes            | My Notes - My Digital Notebook            |
#      | Interview Questions | /interview-questions | Interview Questions - My Digital Notebook |
#      | Blog                | /blog                | Blog - My Digital Notebook                |
#      | Forums              | /forums              | Forums Archive - My Digital Notebook      |
#
#  Scenario: Verify the main search feature is present
#    Then the main search feature should be present
#    And the search should contain a search box
#    And the search box should have a dropdown with items to its right
#    And there should be tags underneath the search box as suggestions to users
#
#  Scenario: Verify the website logo functionality
#    Then the website logo should be present on the top left of the page
#    When I click on the website logo
#    Then I should be redirected to the HomePage
#
#  Scenario: Verify the Register and Login links functionality
#    Then the Register link should be present on the top right
#    And the Login link should be present on the top right
#    When I click on the Register link
#    Then I should be redirected to the Register page
#    When I click on the Login link
#    Then I should be redirected to the Login page
#
#  Scenario: Verify the Forums link functionality
#    Then the Forums link should be present on the top left
#    When I click on the Forums link
#    Then I should be redirected to the Forums page
