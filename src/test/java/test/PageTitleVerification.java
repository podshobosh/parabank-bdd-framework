package test;

import org.openqa.selenium.chrome.ChromeDriver;

public class PageTitleVerification {

    public static void main (String[] args) {

        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String homePageUrl = "https://seleniums.com/";
        String expectedHomePageTitle = "Home - My Digital Notebook";

        driver.get(homePageUrl);

        String actualHomePageTitle = driver.getTitle();

        if(actualHomePageTitle.equals(expectedHomePageTitle)){
            System.out.println("Home Page Title Verification Passed");
        }else {
            System.out.println("Home Page Title Verification Failed");
            System.out.println("Actual Title: " + actualHomePageTitle);
            System.out.println("Expected Title: " + expectedHomePageTitle);
        }
        driver.quit();
    }
}
