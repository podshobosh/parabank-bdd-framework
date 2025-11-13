package pages;

import factory.DriverFactory;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private final WebDriver driver;

    public HomePage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(name = "username")
    private WebElement usernameInputBox;

    @FindBy(name = "password")
    private WebElement passwordInputBox;

    @FindBy(xpath = "//input[@class='button']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[text()='Forgot login info?']")
    private WebElement forgotLoginInfoLink;

    @Getter
    @FindBy(xpath = "//a[text()='Register']")
    private WebElement registerLink;


}
