package pages;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
    private WebElement repeatedPasswordInputBox;

    @FindBy(xpath = "//input[@value='Register']")
    private WebElement registerButton;

    
    public void navigateToRegisterPage(){
       getRegisterLink().click(); 
    }
        
    
    // Fills the registration form using a Map<String, String> coming from a Cucumber DataTable
    // Expected keys: firstName, lastName, address, city, state, zipCode (or zip), phone, ssn, username, password
    public void register(Map<String, String> data) {
        String firstName = data.getOrDefault("firstName", "");
        String lastName = data.getOrDefault("lastName", "");
        String address = data.getOrDefault("address", "");
        String city = data.getOrDefault("city", "");
        String state = data.getOrDefault("state", "");
        String zip = data.getOrDefault("zipCode", data.getOrDefault("zip", ""));
        String phone = data.getOrDefault("phone", "");
        String ssn = data.getOrDefault("ssn", "");
        String username = data.getOrDefault("username", "");
        String password = data.getOrDefault("password", "");

        // Generate random username if contains ${RANDOM}
        if (username.contains("${RANDOM}")) {
            username = username.replace("${RANDOM}",
                    System.currentTimeMillis() + "" + (int)(Math.random() * 1000));
            Log.info("Generated random username: " + username);
        }

        firstNameInputBox.sendKeys(firstName);
        lastNameInputBox.sendKeys(lastName);
        addressInputBox.sendKeys(address);
        cityInputBox.sendKeys(city);
        stateInputBox.sendKeys(state);
        zipInputBox.sendKeys(zip);
        phoneInputBox.sendKeys(phone);
        ssnInputBox.sendKeys(ssn);
        usernameInputBox.sendKeys(username);
        passwordInputBox.sendKeys(password);
        repeatedPasswordInputBox.sendKeys(password);

        Log.info("Filled out all the required fields.");
    }

    public void clickRegisterButton() {
        registerButton.click();
        Log.info("Clicked Register button.");
    }



}
