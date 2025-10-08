package pages;

import factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.HoldOn;

import java.util.List;

public class SearchResultsPage
{
    private WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h5[@class='title']")
    private List<WebElement> resultsLinks;

    public boolean verifyAllSearchResultsContainSearchTerm(String term) {
        HoldOn.waitForPreloaderToDisappear(driver);


        if (resultsLinks.isEmpty()) {
            return false;
        }

        for (WebElement eachLink : resultsLinks) {
            String linkText = eachLink.getText();
            if (!linkText.contains(term)) {
                System.out.println("Result doesn't contain search term: " + linkText);
                return false;
            }
        }

        return true;
    }
}
