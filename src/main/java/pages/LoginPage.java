package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.HoldOn;
import utils.Log;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        if (driver == null) throw new IllegalArgumentException("driver is null");

        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy (xpath = "//a[text()='Login']")
    public WebElement loginLink;

    @FindBy (id = "user-daa95ea")
    public WebElement usernameInputBox;

    @FindBy (id = "password-daa95ea")
    public WebElement passwordInputBox;

    @FindBy (name = "wp-submit")
    public WebElement loginButton;

    @FindBy(xpath = "//div[@id='login_error']//li[normalize-space() = 'ERROR: Incorrect Username or Password']")
    public WebElement errorMessage;


    public void clickLoginLink(){
        HoldOn.safeClick(driver, loginLink);
        Log.info("Verified that the Login link is visible and clicked.");

        // Wait for preloader to disappear after navigation
        HoldOn.waitForPreloaderToDisappear(driver);

    }

    public void login(String user, String pass) {
        usernameInputBox.sendKeys(user);
        passwordInputBox.sendKeys(pass);
        Log.info("Entered username and password credentials");

    }

    public void clickLogin(){
        HoldOn.safeClick(driver, loginButton);
        Log.info("Verified that the Login button is visible and clicked.");

        // Wait for preloader to disappear after navigation
        HoldOn.waitForPreloaderToDisappear(driver);
    }

    public String getErrorText(){
        return errorMessage.getText().trim();
    }

}
