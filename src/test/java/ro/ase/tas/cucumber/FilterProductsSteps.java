package ro.ase.tas.cucumber;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class FilterProductsSteps {
    private WebDriver driver;
    int totalNumberOfMensParfumesDisplayed;

    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://parfumis.myshopify.com/");
    }

    @When("I navigate to men's parfumes catalog")
    public void i_navigate_to_the_catalog() throws InterruptedException {
        driver.findElement(By.xpath("//a[@id='HeaderMenu-parfumuri-barbați']/span")).click();
        Thread.sleep(2000);
    }

    @Then("I count how many men's parfumes are in the catalog")
    public void i_count_how_many_mens_parfumes_are_in_the_catalog() throws InterruptedException {
        // Verificați că numai produsele disponibile sunt afișate în catalog
        totalNumberOfMensParfumesDisplayed = driver.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']")).size();
        System.out.println(totalNumberOfMensParfumesDisplayed);
    }

    @When("I navigate to all parfumes catalog")
    public void i_navigate_to_all_parfumes_catalog() throws InterruptedException {
        driver.findElement(By.xpath("//a[@id='HeaderMenu-produse']/span")).click();
        Thread.sleep(2000);
    }

    @Then("I verify that the same number of men's parfumes are diplayed")
    public void i_verify_that_the_same_number_of_mens_parfumes_are_displayed() throws InterruptedException {
        driver.findElement(By.xpath("//details[@id='Details-filter.p.product_type-template--19522231435532__product-grid']/summary/div/span")).click();
        Thread.sleep(1000);

        WebElement checkbox = driver.findElement(By.id("Filter-filter.p.product_type-1"));

        Actions actions = new Actions(driver);
        actions.moveToElement(checkbox).click().perform();
        Thread.sleep(1000);

        int totalNumberOfMensParfumesDisplayedByFilter = driver.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']")).size();

        assertEquals(totalNumberOfMensParfumesDisplayed, totalNumberOfMensParfumesDisplayedByFilter);

        driver.close();
        driver.quit();
    }
}
