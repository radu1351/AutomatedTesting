package ro.ase.tas.cucumber;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class AddProductToCartSteps {
    private WebDriver driver;
    @Given("I am on the welcome page")
    public void i_am_on_the_welcome_page() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://parfumis.myshopify.com/");
    }

    @When("I add a product to the cart")
    public void i_add_a_product_to_the_cart() throws InterruptedException {
        driver.findElement(By.id("CardLink-template--19522231468300__featured_collection-8686799716620")).click();
        driver.findElement(By.id("ProductSubmitButton-template--19522231632140__main")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("cart-notification-button")).click();
    }

    @Then("I should see the product in my cart")
    public void i_should_see_the_product_in_my_cart() {
        Assertions.assertEquals("Dior Homme (Eau de Toilette)", driver.findElement(By.linkText("Dior Homme (Eau de Toilette)")).getText());
        driver.findElement(By.xpath("//cart-remove-button[@id='Remove-1']/a")).click();
        driver.close();
        driver.quit();
    }
}
