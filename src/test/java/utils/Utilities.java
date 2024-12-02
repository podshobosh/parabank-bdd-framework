package utils;

import base.BaseSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Utilities extends BaseSetup {

    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(20));
    }

    public void clickOnElement(By locator) {
        getWait().until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void sendText(By locator, String value) {
         getWait().until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
    }
}
