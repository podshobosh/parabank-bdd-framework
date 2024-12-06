package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    @FindBy(id = "menu-main-menu")
    private WebElement mainMenu;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Click on a menu item by its index (0 to 4 for 5 items)
    public void clickMenuItem(int index) {
        List<WebElement> menuItems = mainMenu.findElements(By.tagName("li"));
        if (index >= 0 && index < menuItems.size()) {
            menuItems.get(index).click();
        } else {
            throw new IndexOutOfBoundsException("Invalid menu item index.");
        }
    }

    // Get the text of a menu item by its index (0 to 4 for 5 items)
    public String getMenuItemText(int index) {
        List<WebElement> menuItems = mainMenu.findElements(By.tagName("li"));
        if (index >= 0 && index < menuItems.size()) {
            return menuItems.get(index).getText();
        } else {
            throw new IndexOutOfBoundsException("Invalid menu item index.");
        }
    }
}