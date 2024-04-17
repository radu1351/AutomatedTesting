package ro.ase.tas.cucumber;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.junit.jupiter.api.Assertions;
import java.util.regex.Matcher;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.Ordering;
public class SortProductsSteps {
    WebDriver driver;

    @Given("I am on the Parfumis home page")
    public void on_the_parfumis_home_page() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://parfumis.myshopify.com/");
    }

    @When("I navigate to the catalog section")
    public void navigate_to_the_catalog() throws InterruptedException {
        // Navigați la catalog
        driver.findElement(By.xpath("//a[@id='HeaderMenu-produse']/span")).click();
        Thread.sleep(1000);
    }

    @Then("I verify that products are sorted correctly")
    public void i_verify_that_sorted_products_are_displayed() throws InterruptedException {

        driver.findElement(By.id("SortBy")).click();
        new Select(driver.findElement(By.id("SortBy"))).selectByVisibleText("Preț, de la mic la mare");
        driver.findElement(By.xpath("//div[@id='ProductGridContainer']/div")).click();
        List<WebElement> parfumePrices = driver.findElements(By.xpath("//span[@class='price-item price-item--sale price-item--last']"));
        Thread.sleep(1000);

        ArrayList<Double> prices = new ArrayList<>(0);
        for (WebElement bookPrice : parfumePrices) {
            String priceText = bookPrice.getText().replace("lei RON", "").trim();
            Pattern pattern = Pattern.compile("\\d+(,\\d+)?"); // Matches digits with optional comma for decimals
            Matcher matcher = pattern.matcher(priceText);
            if (matcher.find()) {
                String matchedPrice = matcher.group().replace(",", ".");
                Double price = Double.parseDouble(matchedPrice);
                prices.add(price);
            }
        }

        boolean isSorted = Ordering.natural().reverse().isOrdered(prices);
        Assertions.assertTrue(isSorted, "The parfumes are not ordered by price ascending.");
        driver.close();
        driver.quit();
    }
}
