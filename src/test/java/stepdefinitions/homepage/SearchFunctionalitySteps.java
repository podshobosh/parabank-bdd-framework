package stepdefinitions.homepage;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.SearchResultsPage;

public class SearchFunctionalitySteps {

    private WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;

    public SearchFunctionalitySteps() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
        this.searchResultsPage = new SearchResultsPage(driver);
    }



    @Given("user is on the home page")
    public void user_is_on_the_home_page() {
        Assert.assertTrue("Not on home page",homePage.isOnHomePage());
    }

    @When("user enters {string} in the search box")
    public void user_enters_in_the_search_box(String searchTerm) {
        homePage.sendTermToSearchBox(searchTerm);
    }

    @When("user clicks enter")
    public void user_clicks_search_button() {
        homePage.clickEnterInSearchBox();
    }

    @Then("search results for {string} should be displayed")
    public void search_results_for_should_be_displayed(String searchTerm) {
        Assert.assertTrue("Not all search results contain the term: " + searchTerm,searchResultsPage.verifyAllSearchResultsContainSearchTerm(searchTerm));
    }

}
