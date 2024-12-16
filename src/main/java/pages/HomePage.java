package pages;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
    private WebElement topSearchBox;

    // Top Search Box Close Button
    @FindBy(xpath = "//div[@id='navbarSupportedContent']/div/div[2]")
    private WebElement closeTopSearchBox;

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
        HoldOn.waitForElementToBeVisible(driver, topSearchBox);
        Log.info("Verified that the top search box is visible.");
    }

    public void verifyTopSearchBoxIsClosed() {
        HoldOn.waitForElementToBeVisible(driver, closeTopSearchBox);
        closeTopSearchBox.click();
        Log.info("Closed the top search box.");
    }

    // Main Search Bar
    public void verifyMainSearchBoxVisible() {
        HoldOn.waitForElementToBeVisible(driver, mainSearchBar);
        Log.info("Verified that the main search bar is visible.");
    }

    // =================== Future Features ===================
    // Uncomment and implement these methods as needed

    // Example for handling Main Menu
    // @FindBy(id = "main-menu")
    // private WebElement mainMenu;

    // Example for handling Light/Dark Mode
    // @FindBy(id = "theme-toggle")
    // private WebElement themeToggleButton;

    // public void toggleTheme() {
    //     HoldOn.waitForElementToBeClickable(driver, themeToggleButton).click();
    //     Log.info("Toggled the theme.");
    // }

    // Example for handling User Links (e.g., Register, Login)
    // @FindBy(xpath = "//a[text()='Register']")
    // private WebElement registerLink;

    // @FindBy(xpath = "//a[text()='Login']")
    // private WebElement loginLink;

    // public void clickRegisterLink() {
    //     HoldOn.waitForElementToBeClickable(driver, registerLink).click();
    //     Log.info("Clicked on the Register link.");
    // }

    // public void clickLoginLink() {
    //     HoldOn.waitForElementToBeClickable(driver, loginLink).click();
    //     Log.info("Clicked on the Login link.");
    // }
}