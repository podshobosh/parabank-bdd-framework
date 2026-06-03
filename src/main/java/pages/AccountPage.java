package pages;

import factory.DriverFactory;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.AppUrls;
import utils.CommonUtils;
import utils.HoldOn;

public class AccountPage extends HomePage {
    private final WebDriver driver;

    public AccountPage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(this.driver, this);
    }

    @Getter
    @FindBy(xpath = "//div[@id='rightPanel']/h1")
    private WebElement welcomeUserHeader;

    @Getter
    @FindBy(xpath = "//p[contains(.,'Your account was created successfully')]")
    private WebElement successMessage;

    @FindBy(xpath = "//h1[contains(.,'Accounts Overview')]")
    private WebElement accountOverviewText;

    @Getter
    @FindBy(xpath = "//h1[contains(.,'Open New Account')]")
    private WebElement openNewAccountMessage;

    @FindBy(xpath = "//input[@value='Open New Account']")
    private WebElement openAccountButton;

    @FindBy(id = "type")
    private WebElement accountTypeDropdown;

    @FindBy(id = "fromAccountId")
    private WebElement existingAccountDropdown;

    @Getter
    @FindBy(xpath = "//h1[text()='Account Opened!']")
    private WebElement accountOpenedMessage;

    @Getter
    @FindBy(xpath = "//p[contains(.,'Congratulations')]")
    private WebElement congratulationMessage;

    @FindBy(id = "newAccountId")
    private WebElement newAccountIdLink;

    public void openAccountsOverviewPage() {
        driver.get(AppUrls.BASE_URL + "/overview.htm");
        HoldOn.waitForPageToLoad(driver);
    }

    public boolean isWelcomeUserVisible() {
        HoldOn.waitForElementToBeVisible(driver, welcomeUserHeader);
        return welcomeUserHeader.isDisplayed();
    }

    public boolean isAccountOverviewTextDisplayed() {
        HoldOn.waitForElementToBeVisible(driver, accountOverviewText);
        return accountOverviewText.isDisplayed();
    }

    public boolean isWelcomeMessageVisible() {
        HoldOn.waitForElementToBeVisible(driver, successMessage);
        return successMessage.isDisplayed();
    }

    public void goToNewAccount() {
        driver.get(AppUrls.BASE_URL + "/openaccount.htm");
        HoldOn.waitForElementToBeVisible(driver, openNewAccountMessage);
    }

    public void highlightMessage() {
        CommonUtils.highlight(driver, openNewAccountMessage);
    }

    public void selectCheckingAccount() {
        HoldOn.waitForElementToBeVisible(driver, accountTypeDropdown);
        CommonUtils.selectFromDropdownWithText(accountTypeDropdown, "CHECKING");
    }

    public void selectDepositAccount() {
        HoldOn.waitForElementToBeVisible(driver, existingAccountDropdown);
        CommonUtils.selectFromDropdownWithIndex(existingAccountDropdown, 0);
    }

    public void clickOpenAccountBttn() {
        HoldOn.waitForElementToBeClickable(driver, openAccountButton);
        openAccountButton.click();
    }

    public boolean isConfirmationMessageVisible() {
        HoldOn.waitForElementToBeVisible(driver, accountOpenedMessage);
        return accountOpenedMessage.isDisplayed();
    }

    public int getNewAccountId() {
        HoldOn.waitForElementToBeVisible(driver, newAccountIdLink);
        return Integer.parseInt(newAccountIdLink.getText().trim());
    }
}
