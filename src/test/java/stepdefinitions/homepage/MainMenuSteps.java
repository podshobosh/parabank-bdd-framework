package stepdefinitions.homepage;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import utils.CommonUtils;
import utils.HoldOn;
import utils.Log;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

public class MainMenuSteps {

    private WebDriver driver;
    private HomePage homePage;

    public MainMenuSteps() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
    }

    // Helper Method to Check if User is on the Home Page
    private boolean isOnHomePage() {
        String currentUrl = driver.getCurrentUrl();
        return "https://www.seleniums.com/".equals(currentUrl);
    }

    @Given("the main menu contains the following options:")
    public void verifyMainMenuContainsOptions(DataTable dataTable) {
        Log.info("Step started: Verifying that the main menu contains the expected options.");

        List<String> expectedOptions = dataTable.asList();
        Log.info("Expected menu options from feature file: " + expectedOptions);

        try {
            HoldOn.waitForElementToBeVisible(driver, homePage.getMainMenuElement());
            Log.info("Main menu is visible.");

            List<String> actualOptions = homePage.getMainMenuItemsText();
            Log.info("Actual menu options retrieved from the page: " + actualOptions);

            // Verify number of menu options
            if (expectedOptions.size() != actualOptions.size()) {
                String errorMessage = "Number of menu options mismatch. Expected: " + expectedOptions.size() + ", Actual: " + actualOptions.size();
                Log.error(errorMessage);
                throw new AssertionError(errorMessage);
            }
            Log.info("Number of menu options matches the expected count.");

            // Verify each menu option text
            for (int i = 0; i < expectedOptions.size(); i++) {
                if (!expectedOptions.get(i).equals(actualOptions.get(i))) {
                    String errorMessage = "Menu option mismatch at index " + i + ". Expected: " + expectedOptions.get(i) + ", Actual: " + actualOptions.get(i);
                    Log.error(errorMessage);
                    throw new AssertionError(errorMessage);
                }
                Log.info("Verified menu option text at index " + i + ": " + actualOptions.get(i));
            }

            Log.info("Step completed successfully: Main menu contains the expected options.");
        } catch (AssertionError e) {
            Log.error("Assertion error during main menu verification: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Log.error("Unexpected error during main menu verification.", e);
            throw e;
        }
    }

    @Then("all menu options should be displayed and clickable")
    public void verifyMenuOptionsAreDisplayedAndClickable() {
        Log.info("Step started: Verifying that all menu options are displayed and clickable.");

        try {
            HoldOn.waitForElementToBeVisible(driver, homePage.getMainMenuElement());
            Log.info("Main menu is visible.");

            for (String menuItemText : homePage.getMainMenuItemsText()) {
                boolean isDisplayedAndClickable = homePage.isMainMenuItemDisplayedAndClickable(menuItemText);
                if (!isDisplayedAndClickable) {
                    String errorMessage = "Menu item '" + menuItemText + "' is not displayed or clickable.";
                    Log.error(errorMessage);
                    throw new AssertionError(errorMessage);
                }
                Log.info("Menu item '" + menuItemText + "' is displayed and clickable.");
            }

            Log.info("Step completed successfully: All menu options are displayed and clickable.");
        } catch (AssertionError e) {
            Log.error("Assertion error during menu options' display and clickability verification: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Log.error("Unexpected error during menu options' display and clickability verification.", e);
            throw e;
        }
    }

    @When("I click on each menu option")
    public void verifyMenuNavigationAndReturn(DataTable dataTable) {
        Log.info("Step started: Verifying navigation for each menu option.");

        List<List<String>> rows = dataTable.asLists(String.class);

        boolean isHomePage = isOnHomePage();
        if (isHomePage) {
            Log.info("User is already on the Home page. Skipping initial logo click.");
        }

        for (List<String> row : rows) {
            String menuOption = row.get(0);
            String expectedUrl = row.get(1);
            String expectedTitle = row.get(2);

            try {
                homePage.clickMainMenuItemByName(menuOption);
                Log.info("Clicked on menu option: " + menuOption);

                HoldOn.waitForPageToLoad(driver);
                String actualUrl = driver.getCurrentUrl();
                Log.info("Actual URL after clicking '" + menuOption + "': " + actualUrl);

                CommonUtils.verifyUrl(driver, expectedUrl);
                CommonUtils.verifyTitle(driver, expectedTitle);

                // Skip returning to Home page for the first iteration if already on Home page
                if (!isHomePage) {
                    homePage.clickSiteLogo();
                    HoldOn.waitForPageToLoad(driver);

                    CommonUtils.verifyUrl(driver, "https://www.seleniums.com/");
                    CommonUtils.verifyTitle(driver, "Home - My Digital Notebook");

                    Log.info("Returned to Home page after verifying menu option: " + menuOption);
                } else {
                    isHomePage = false; // Reset the flag after the first iteration
                    Log.info("Skipped returning to Home page for the first iteration.");
                }
            } catch (AssertionError e) {
                Log.error("Assertion error during navigation verification for menu option '" + menuOption + "': " + e.getMessage());
                throw e;
            } catch (Exception e) {
                Log.error("Unexpected error during navigation verification for menu option '" + menuOption + "'.", e);
                throw e;
            }
        }

        Log.info("Step completed successfully: Verified navigation for all menu options.");
    }
}
