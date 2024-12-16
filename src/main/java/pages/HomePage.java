package pages;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonUtils;
import utils.HoldOn;
import utils.Log;

public class HomePage {

    private WebDriver driver;

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = DriverFactory.getDriver();  // Initialize driver from DriverFactory
        PageFactory.initElements(driver, this);   // Initialize elements
    }

    // =================== Locators ===================

    // Top Search Button Icon
    @FindBy(xpath = "//*[@id='navbarSupportedContent']/div/div[2]")
    private WebElement topSearchBtnIcon;

    // Top Search Box
    @FindBy(xpath = "//*[@id='navbarSupportedContent']/form/input")
    private WebElement topSearchInputBox;

    // Main Search Bar
    @FindBy(id = "searchInput")
    private WebElement mainSearchBar;

    // =================== Actions ===================

    // Top Search Icon
    public void verifyTopSearchIconVisible() {
        HoldOn.waitForElementToBeVisible(driver, topSearchBtnIcon);
        Log.info("Verified that the top search icon is visible.");
    }

    public void clickTopSearchIcon() {
        HoldOn.waitForElementToBeVisible(driver, topSearchBtnIcon);
        topSearchBtnIcon.click();
        Log.info("Clicked on the top search icon.");
    }

    // Top Search Box
    public void verifyTopSearchBoxIsVisible() {
        HoldOn.waitForElementToBeVisible(driver, topSearchInputBox);
        Log.info("Verified that the top search box is visible.");
    }

    public void verifyTopSearchBoxIsClosed() {
        HoldOn.waitForElementToBeVisible(driver, topSearchBtnIcon);
        topSearchBtnIcon.click();
        Log.info("Closed the top search box.");
    }

    public String getTopSearchIconClassAttribute() {
        Log.info("Attempting to retrieve the 'class' attribute of the top search icon.");
        String classAttribute = CommonUtils.getAttribute(topSearchBtnIcon, "class");

        if (classAttribute != null) {
            Log.info("Successfully retrieved 'class' attribute: " + classAttribute);
        } else {
            Log.warn("The 'class' attribute could not be retrieved. Element may not be interactable.");
        }
        return classAttribute;
    }

    /**
     * Verifies the state of the search box (open or closed) based on the expected state.
     *
     * @param shouldBeOpen true if the search box is expected to be open, false if expected to be closed.
     */
    public void topSearchBoxShouldBeOpen(boolean shouldBeOpen) {
        String classAttribute = getTopSearchIconClassAttribute();
        boolean isExpanded = classAttribute != null && classAttribute.contains("show-close");

        if (shouldBeOpen && isExpanded) {
            Log.info("Search box is open as expected." + "Class Attribute: " + classAttribute);
        } else if (!shouldBeOpen && !isExpanded) {
            Log.info("Search box is closed as expected." + "Class Attribute: " + classAttribute);
        } else {
            String errorMessage = shouldBeOpen
                    ? "Search box is not open when it should be."
                    : "Search box is not closed when it should be.";
            Log.error(errorMessage);
            throw new AssertionError(errorMessage);
        }
    }
}