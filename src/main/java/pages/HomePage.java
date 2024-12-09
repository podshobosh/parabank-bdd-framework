package pages;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Log;
import utils.HoldOn;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = DriverFactory.getDriver();  // Initialize driver from DriverFactory
        PageFactory.initElements(driver, this);   // Initialize elements
    }

    // Top Search Button Icon
    @FindBy(xpath = "//*[@id='navbarSupportedContent']/div/div[2]")
    private WebElement topSearchBtnIcon;

    public void verifyTopSearchIconVisible() {
        // Wait for the top search button icon to be visible
        HoldOn.waitForElementToBeVisible(driver, topSearchBtnIcon);
    }

    public void clickTopSearchIcon() {
        // Wait for the top search button icon to be visible
        HoldOn.waitForElementToBeVisible(driver, topSearchBtnIcon);
        // Click the top search button icon
        topSearchBtnIcon.click();
    }

    // Top Search Box
    @FindBy(xpath = "//*[@id='navbarSupportedContent']/form/input")
    private WebElement topSearchBox;

    public void verifyTopSearchBoxIsVisible() {
        // Wait for the top search box to be visible
        HoldOn.waitForElementToBeVisible(driver, topSearchBox);
    }

    // Top Search Box Close and Verify not visible
    @FindBy(xpath = "//div[@id='navbarSupportedContent']/div/div[2]")
    private WebElement closeTopSearchBox;

    public void verifyTopSearchBoxIsClosed() {
        HoldOn.waitForElementToBeVisible(driver, closeTopSearchBox);
        closeTopSearchBox.click();
        Log.info("Top search box is closed now...");
    }

    // Main Search Box
    @FindBy(id = "searchInput")
    private WebElement mainSearchBar;

    public void verifyMainSearchBox() {
        // Wait for the main search box to be visible
        HoldOn.waitForElementToBeVisible(driver, mainSearchBar);
    }


//    // Main Menu
//    @FindBy(id = "hidden_post_type")
//    private WebElement mainSearchDropdown;
//
//    @FindBy(css = ".search-tags")
//    private List<WebElement> searchTags;
//
//    // Light/Dark Mode
//    @FindBy(id = "theme-toggle")
//    private WebElement lightDarkToggleButton;
//
//    // Main Menu
//    @FindBy(id = "main-menu")
//    private WebElement mainMenu;
//
//    @FindBy(xpath = "//ul[@id='main-menu']/li")
//    private List<WebElement> menuOptions;
//
//    // Website Logo
//    @FindBy(css = "a[href='/']")
//    private WebElement websiteLogo;
//
//    // User Links
//    @FindBy(xpath = "//a[text()='Register']")
//    private WebElement registerLink;
//
//    @FindBy(xpath = "//a[text()='Login']")
//    private WebElement loginLink;
//
//    // Forums
//    @FindBy(xpath = "//a[text()='Forums']")
//    private WebElement forumsLink;
//
//    // Actions
//
//    // Search Section
//    public void clickSearchButton() {
//        Wait.waitForElementToBeClickable(driver, topSearchButtonIcon).click();
//    }
//
//    public void enterSearchQuery(String query) {
//        Wait.waitForElementToBeVisible(driver, topSearchBox).sendKeys(query);
//    }
//
//    public boolean isSearchBoxExpanded() {
//        return topSearchBox.getAttribute("class").contains("expanded");
//    }
//
//    public boolean isSearchDropdownDisplayed() {
//        return mainSearchDropdown.isDisplayed();
//    }
//
//    public List<String> getSearchTagsText() {
//        return searchTags.stream().map(WebElement::getText).toList();
//    }
//
//    // Light/Dark Mode
//    public void toggleTheme() {
//        Wait.waitForElementToBeClickable(driver, lightDarkToggleButton).click();
//    }
//
//    // Main Menu
//    public List<String> getMenuOptionsText() {
//        return menuOptions.stream().map(WebElement::getText).toList();
//    }
//
//    public void clickMenuOption(String optionText) {
//        for (WebElement option : menuOptions) {
//            if (option.getText().equalsIgnoreCase(optionText)) {
//                option.click();
//                break;
//            }
//        }
//    }
//
//    // Website Logo
//    public void clickWebsiteLogo() {
//        Wait.waitForElementToBeClickable(driver, websiteLogo).click();
//    }
//
//    // User Links
//    public void clickRegisterLink() {
//        Wait.waitForElementToBeClickable(driver, registerLink).click();
//    }
//
//    public void clickLoginLink() {
//        Wait.waitForElementToBeClickable(driver, loginLink).click();
//    }
//
//    // Forums
//    public void clickForumsLink() {
//        Wait.waitForElementToBeClickable(driver, forumsLink).click();
//    }
//
//
//
//// Click on a menu item by its index (0 to 4 for 5 items)
//    public void clickMenuItem(int index) {
//        Wait.waitForElementToBeVisible(driver, By.id("menu-main-menu"));  // Wait for the main menu to be visible
//        List<WebElement> menuItems = mainMenu.findElements(By.tagName("li"));
//        if (index >= 0 && index < menuItems.size()) {
//            menuItems.get(index).click();
//        } else {
//            throw new IndexOutOfBoundsException("Invalid menu item index.");
//        }
//    }
//
//    // Get the text of a menu item by its index (0 to 4 for 5 items)
//    public String getMenuItemText(int index) {
//        Wait.waitForElementToBeVisible(driver, By.id("menu-main-menu"));  // Wait for the main menu to be visible
//        List<WebElement> menuItems = mainMenu.findElements(By.tagName("li"));
//        if (index >= 0 && index < menuItems.size()) {
//            return menuItems.get(index).getText();
//        } else {
//            throw new IndexOutOfBoundsException("Invalid menu item index.");
//        }
//    }
}