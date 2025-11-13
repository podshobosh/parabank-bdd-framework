package pages;

import factory.DriverFactory;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Log;

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

    public boolean isWelcomeUserVisible(){
       if (welcomeUserHeader.isDisplayed()){
           Log.info("Welcome {username} header is visible");
           return true;
       }
       Log.error("Welcome {username} header is not visible");
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

}

