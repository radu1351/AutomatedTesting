package ro.ase.tas.cucumber;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class SearchProductSteps {
    private WebDriver driver;
    private String url = "https://parfumis.myshopify.com/";

    @Given("I am on the home page for search")
    public void i_am_on_the_home_page_for_search() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(url);;
    }

    @When("I search for {string}")
    public void i_search_for(String productName) {
        WebElement searchIcon = driver.findElement(By.className("header__search"));
        searchIcon.click();
        WebElement searchInput = driver.findElement(By.id("Search-In-Modal"));
        searchInput.sendKeys(productName);
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).build().perform();
    }

    @Then("I should see a list of {string} in the search results")
    public void i_should_see_a_list_of_in_the_search_results(String expectedProductName) {
        List<WebElement> productResults = driver.findElements(By.xpath("//ul[contains(@class,'product-grid')]//li"));
        boolean productFound = false;
        for (WebElement product : productResults) {
            String productTitle = product.findElement(By.xpath(".//a[contains(@id, 'CardLink')]")).getText();
            if (productTitle.contains(expectedProductName)) {
                productFound = true;
                break;
            }
        }
        if (!productFound) {
            throw new AssertionError("Product '" + expectedProductName + "' not found in search results");
        }
        driver.close();
        driver.quit();
    }
}
