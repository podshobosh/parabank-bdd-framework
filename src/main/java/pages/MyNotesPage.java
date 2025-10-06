package pages;

import dev.failsafe.internal.util.Assert;
import factory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.HoldOn;
import utils.Log;

import javax.xml.xpath.XPath;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.XMLFormatter;

public class MyNotesPage {

    private WebDriver driver;

    public MyNotesPage(WebDriver driver) {
        if (driver == null) throw new IllegalArgumentException("driver is null");

        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//li[@id='menu-item-5286']/a")
    private WebElement myNotesLink;


    // dynamic list -> By (re-find fresh each time)
    private final By topicLinksBy = By.xpath("//ul[@class='ezd-list-unstyled nav-sidebar left-sidebar-results ezd-list-unstyled']/li");

    @FindBy(xpath = "//ul[@class='ezd-list-unstyled nav-sidebar left-sidebar-results ezd-list-unstyled']/li")
    List<WebElement> topicLinks;


    @FindBy(xpath = "//h1[text()='Selenium']")
    private WebElement seleniumTitle;

    @FindBy(xpath = "//div[@class='shortcode_title']/h1")
    private WebElement topicTitle;


    public List<WebElement> getTopicLinks() {
        return driver.findElements(topicLinksBy);
    }


    public void clickMyNotesLink() {
        HoldOn.safeClick(driver, myNotesLink);
        Log.info("Verified that the My Notes Tab is visible.");


        // Wait for preloader to disappear after navigation
        HoldOn.waitForPreloaderToDisappear(driver);

        // Wait for topic links to be visible
        HoldOn.waitForElementsToBeVisible(driver, getTopicLinks());

    }

    public void validateTopicLinks() {
        // Wait for preloader first
        HoldOn.waitForPreloaderToDisappear(driver);

        HoldOn.waitForElementsToBeVisible(driver, getTopicLinks());
        Log.info("Topic links are present");

    }

    public void clickOnSeleniumTopicLink() {
        HoldOn.clickOnElementInList(driver, topicLinksBy, "Selenium");
        Log.info("Clicked on Selenium link");

    }

    public void validateSeleniumTitle() {
        HoldOn.waitForElementToBeVisible(driver, seleniumTitle);
        Log.info("Verified that on selenium page");

    }

    public void clickOnSidebarTopicLink(String link) {
        HoldOn.waitForPreloaderToDisappear(driver);

        HoldOn.clickOnElementInList(driver, topicLinks, link);
        Log.info("Clicked on " + link + "link");
        HoldOn.waitForPreloaderToDisappear(driver);

    }

    public void waitForTopicTitle(String expected) {
        // Wait for preloader first
        HoldOn.waitForPreloaderToDisappear(driver);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElement(topicTitle, expected));
    }

    public String getTopicTitle() {
        // Ensure preloader is gone before getting text
        HoldOn.waitForPreloaderToDisappear(driver);

        HoldOn.waitForElementToBeVisible(driver, topicTitle);
        return topicTitle.getText().trim();
    }


}