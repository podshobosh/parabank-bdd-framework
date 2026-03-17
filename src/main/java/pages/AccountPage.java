package pages;

import factory.DriverFactory;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonUtils;
import utils.HoldOn;
import utils.Log;

import java.util.List;

public class AccountPage extends HomePage {
    private final WebDriver driver;

    public AccountPage() {

        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this.driver);
    }
    @Getter
    @FindBy(xpath = "//div[@id ='rightPanel']/h1")
    private WebElement welcomeUserHeader;


    @Getter
    @FindBy(xpath = "//p[text()='Your account was created successfully. You are now logged in.']")
    private WebElement successMessage;

    @FindBy(xpath = "//h1[contains(.,'Accounts Overview')]")
    private WebElement accountOverviewText;

    @FindBy(xpath = "//div[@id='leftPanel']/ul/li")
    private List<WebElement> accountServicesLinks;

    @Getter
    @FindBy(xpath = "//h1[contains(text(), 'Open New Account')]")
    private WebElement openNewAccountMessage;

    @FindBy(xpath = "//input[@type='button']")
    private WebElement openAccountButton;

    @FindBy(id = "type")
    private WebElement accountTypeDropdown;

    @FindBy(id = "type")
    private WebElement existingAccountDropdown;

    @Getter
    @FindBy(xpath = "//h1[text()='Account Opened!']")
    private WebElement accountOpenedMessage;

    @Getter
    @FindBy(xpath = "//p[contains(.,'Congratulations')]")
    private WebElement congratulationMessage;


    private final By accServices = By.xpath("//div[@id='leftPanel']/ul/li");








    public boolean isWelcomeUserVisible(){
       if (welcomeUserHeader.isDisplayed()){
           Log.info("Welcome {username} header is visible");
           return true;
       }
       Log.error("Welcome {username} header is not visible");
        return false;

    }

    public boolean isAccountOverviewTextDisplayed(){
        if (accountOverviewText.isDisplayed()){
            Log.info("Account Overview is displayed");
            return true;
        }
        Log.error("Account Overview test is not displayed");
        return false;
    }

    public boolean isWelcomeMessageVisible(){
        if (successMessage.isDisplayed()){
            Log.info("'User created successfully' message is visible");
            return true;
        }
        Log.error("'User created successfully' message is NOT visible");
        return false;
    }

    public void clickOnSelectService(List<WebElement> services, String serviceText){
        for (WebElement service : services) {
            if (service.getText().contains(serviceText)){
                CommonUtils.highlight(driver, service);
                CommonUtils.sleep(5);
                HoldOn.safeClick(driver, service);
            }
        }
    }

    public void goToNewAccount(){
        //clickOnSelectService(accountServicesLinks, "Open New Account");
        HoldOn.clickOnElementInList(driver, accServices, "Open New Account");
    }

    public void highlightMessage(){
        CommonUtils.highlight(driver,openNewAccountMessage);
    }

    public void selectCheckingAccount(){
        CommonUtils.highlight(driver, accountTypeDropdown);
        CommonUtils.selectFromDropdownWithText(accountTypeDropdown, "CHECKING");
    }

    public void selectDepositAccount(){
        CommonUtils.highlight(driver, existingAccountDropdown);
       CommonUtils.selectFromDropdownWithIndex(existingAccountDropdown, 0);
    }

    public void clickOpenAccountBttn(){
        openAccountButton.click();
    }

    public boolean isConfirmationMessageVisible(){
        HoldOn.waitForElementToBeVisible(driver, accountOpenedMessage);
        return accountOpenedMessage.isDisplayed();
    }






}

