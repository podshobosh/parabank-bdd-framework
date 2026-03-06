package pages;

import factory.DriverFactory;
import models.Customer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.AppUrls;
import utils.Log;

import java.util.Map;

public class RegisterPage extends HomePage {
    private final WebDriver driver;

    public RegisterPage() {

        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(this.driver, this);

    }

    @FindBy(id = "customer.firstName")
    private WebElement firstNameInputBox;

    @FindBy(id = "customer.lastName")
    private WebElement lastNameInputBox;

    @FindBy(id = "customer.address.street")
    private WebElement addressInputBox;

    @FindBy(id = "customer.address.city")
    private WebElement cityInputBox;

    @FindBy(id = "customer.address.state")
    private WebElement stateInputBox;

    @FindBy(id = "customer.address.zipCode")
    private WebElement zipInputBox;

    @FindBy(id = "customer.phoneNumber")
    private WebElement phoneInputBox;

    @FindBy(id = "customer.ssn")
    private WebElement ssnInputBox;

    @FindBy(id = "customer.username")
    private WebElement usernameInputBox;

    @FindBy(id = "customer.password")
    private WebElement passwordInputBox;

    @FindBy(id = "repeatedPassword")
    private WebElement confirmPassInputBox;

    @FindBy(xpath = "//input[@value='Register']")
    private WebElement registerButton;


    public void navigateToRegisterPage() {
        getRegisterLink().click();
    }


    public void openRegisterPage() {
        driver.get(AppUrls.REGISTRATION_PAGE);
    }

    public boolean isOnRegistrationPage() {
        return driver.getCurrentUrl().contains("register");
    }

    public void register(Customer customer) {
        type(firstNameInputBox, customer.getFirstName());
        type(lastNameInputBox, customer.getLastName());
        type(addressInputBox, customer.getAddress());
        type(cityInputBox, customer.getCity());
        type(stateInputBox, customer.getState());
        type(zipInputBox, customer.getZip());
        type(phoneInputBox, customer.getPhone());
        type(ssnInputBox, customer.getSsn());

        type(usernameInputBox, customer.getUsername());
        type(passwordInputBox, customer.getPassword());
        type(confirmPassInputBox, customer.getPassword());

        clickRegisterButton();
    }

    public void clickRegisterButton() {
        registerButton.click();
        Log.info("Clicked Register button.");
    }

    private void type(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }


}
