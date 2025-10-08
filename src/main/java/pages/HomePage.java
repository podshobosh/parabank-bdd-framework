package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.HoldOn;
import utils.Log;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='navbarSupportedContent']/div/div[2]")
    private WebElement topSearchBtnIcon;

    @FindBy(xpath = "//*[@id='navbarSupportedContent']/form/input")
    private WebElement topSearchInputBox;

    @FindBy(id = "searchInput")
    private WebElement mainSearchBar;

    @FindBy(xpath = "//ul[@id='menu-main-menu']/li/a")
    private List<WebElement> websiteMainMenu;

    @FindBy(xpath = "//ul[@id='menu-main-menu']")
    private WebElement mainMenuElement;

    @FindBy(xpath = "//*[@id='sticky']/div/a/img[2]")
    private WebElement siteLogo;


    public void sendTermToSearchBox(String term) {
        HoldOn.waitForElementToBeVisible(driver, mainSearchBar);
        mainSearchBar.sendKeys(term);
        Log.info("Sent search term into the search box.");
    }

    public void clickEnterInSearchBox() {
        mainSearchBar.sendKeys(Keys.ENTER);
    }


    public void verifyTopSearchIconVisible() {
        HoldOn.waitForElementToBeVisible(driver, topSearchBtnIcon);
        Log.info("Verified that the top search icon is visible.");
    }

    public void clickTopSearchIcon() {
        HoldOn.waitForElementToBeVisible(driver, topSearchBtnIcon);
        topSearchBtnIcon.click();
        Log.info("Clicked on the top search icon.");
    }

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
        String classAttribute = topSearchBtnIcon.getAttribute("class");
        if (classAttribute != null) {
            Log.info("Retrieved 'class' attribute: " + classAttribute);
        } else {
            Log.warn("The 'class' attribute could not be retrieved. Element may not be interactable.");
        }
        return classAttribute;
    }

    public WebElement siteLogo() {
        return siteLogo;
    }

    public boolean isWebsiteLogoVisible(){
        HoldOn.waitForPreloaderToDisappear(driver);
        HoldOn.waitForElementToBeVisible(driver, siteLogo);
        return siteLogo.isDisplayed();
    }

    public void clickSiteLogo() {
        HoldOn.waitForPreloaderToDisappear(driver);
        HoldOn.waitForElementToBeClickable(driver, siteLogo);
        siteLogo.click();
        Log.info("Clicked on the site logo.");
    }

    public void clickMainMenuItemByName(String itemName) {
        HoldOn.waitForPreloaderToDisappear(driver);

        for (WebElement menuItem : websiteMainMenu) {
            if (menuItem.getText().trim().equalsIgnoreCase(itemName)) {
                HoldOn.waitForElementToBeClickable(driver, menuItem);
                menuItem.click();
                Log.info("Clicked on menu item: " + itemName);
                return;
            }
        }
        throw new RuntimeException("Menu item with name '" + itemName + "' not found.");
    }

    public WebElement getMainMenuElement() {
        HoldOn.waitForElementToBeVisible(driver, mainMenuElement);
        Log.info("Retrieved main menu element.");
        return mainMenuElement;
    }

    public List<String> getMainMenuItemsText() {
        List<String> menuItemsText = new ArrayList<>();
        HoldOn.waitForElementToBeVisible(driver, mainMenuElement);

        for (WebElement menuItem : websiteMainMenu) {
            String text = menuItem.getText().trim();
            menuItemsText.add(text);
            Log.info("Retrieved menu item text: " + text);
        }

        Log.info("All menu items text retrieved: " + menuItemsText);
        return menuItemsText;
    }

    public boolean isMainMenuItemDisplayedAndClickable(String menuItemText) {
        for (WebElement menuItem : websiteMainMenu) {
            if (menuItem.getText().trim().equalsIgnoreCase(menuItemText)) {
                HoldOn.waitForElementToBeVisible(driver, menuItem);
                return menuItem.isDisplayed() && menuItem.isEnabled();
            }
        }
        throw new RuntimeException("Menu item with name '" + menuItemText + "' not found.");
    }

    public void topSearchBoxShouldBeOpen(boolean shouldBeOpen) {
        String classAttribute = getTopSearchIconClassAttribute();
        boolean isExpanded = classAttribute != null && classAttribute.contains("show-close");

        if (shouldBeOpen && isExpanded) {
            Log.info("Search box is open as expected. Class Attribute: " + classAttribute);
        } else if (!shouldBeOpen && !isExpanded) {
            Log.info("Search box is closed as expected. Class Attribute: " + classAttribute);
        } else {
            String errorMessage = shouldBeOpen
                    ? "Search box is not open when it should be."
                    : "Search box is not closed when it should be.";
            Log.error(errorMessage);
            throw new AssertionError(errorMessage);
        }
    }

    public boolean isOnHomePage() {
        String currentUrl = driver.getCurrentUrl();
        return "https://www.seleniums.com/".equals(currentUrl);
    }
}