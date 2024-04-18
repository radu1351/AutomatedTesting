package ro.ase.tas.selenium;

import com.google.common.collect.Ordering;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomationClassTest {
    public WebDriver driver;
    public String url = "https://parfumis.myshopify.com/";

    public void setupSite() {
        driver.get(url);
    }

    @BeforeEach
    public void setupApplication() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void closeApplication() {
        driver.close();
        driver.quit();
    }

    @Test
    public void searchProduct() throws InterruptedException {
        setupSite();
        driver.findElement(By.className("header__search")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("Search-In-Modal")).sendKeys("Dior Sauvage");
        Thread.sleep(1000);
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).build().perform();
        List<WebElement> parfumes = driver.findElements(By.xpath("//ul[contains(@class,'product-grid')]//li"));
        for (WebElement watch : parfumes) {
            String watchTitle = watch.findElement(By.xpath(".//a[contains(@id, 'CardLink')]")).getText();
            System.out.println(watchTitle);
        }
        boolean match = parfumes.stream().anyMatch(w -> w.findElement(By.xpath(".//a[contains(@id, 'CardLink')]")).getText().contains("Dior Sauvage"));
        Assertions.assertTrue(match);
    }

    @Test
    public void addProductToCart() throws InterruptedException {
        setupSite();
        driver.findElement(By.id("CardLink-template--19522231468300__featured_collection-8686799716620")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ProductSubmitButton-template--19522231632140__main")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("cart-notification-button")).click();
        Thread.sleep(1000);
        Assertions.assertEquals("Dior Homme (Eau de Toilette)", driver.findElement(By.linkText("Dior Homme (Eau de Toilette)")).getText());
        driver.findElement(By.xpath("//cart-remove-button[@id='Remove-1']/a")).click();
    }

    @Test
    public void FilterProducts() throws InterruptedException {
        driver.get(url);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[@id='HeaderMenu-parfumuri-barbați']/span")).click();
        Thread.sleep(1000);
        int totalNumberOfMensParfumesDisplayed = driver.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']")).size();
        driver.findElement(By.xpath("//a[@id='HeaderMenu-produse']/span")).click();
        driver.findElement(By.xpath("//details[@id='Details-filter.p.product_type-template--19522231435532__product-grid']/summary/div/span")).click();
        Thread.sleep(1000);
        WebElement checkbox = driver.findElement(By.id("Filter-filter.p.product_type-1"));
        Actions actions = new Actions(driver);
        actions.moveToElement(checkbox).click().perform();
        Thread.sleep(1000);
        int totalNumberOfMensParfumesDisplayedByFilter = driver.findElements(By.xpath("//div[@class='card-wrapper product-card-wrapper underline-links-hover']")).size();
        assertEquals(totalNumberOfMensParfumesDisplayed, totalNumberOfMensParfumesDisplayedByFilter);
    }

    @Test
    public void SortProducts() throws InterruptedException {
        setupSite();
        driver.findElement(By.xpath("//a[@id='HeaderMenu-produse']/span")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("SortBy")).click();
        new Select(driver.findElement(By.id("SortBy"))).selectByVisibleText("Preț, de la mic la mare");
        driver.findElement(By.xpath("//div[@id='ProductGridContainer']/div")).click();
        List<WebElement> parfumePrices = driver.findElements(By.xpath("//span[@class='price-item price-item--sale price-item--last']"));
        Thread.sleep(1000);
        ArrayList<Double> prices = new ArrayList<>(0);
        for (WebElement bookPrice : parfumePrices) {
            String priceText = bookPrice.getText().replace("lei RON", "").trim();
            Pattern pattern = Pattern.compile("\\d+(,\\d+)?");
            Matcher matcher = pattern.matcher(priceText);
            if (matcher.find()) {
                String matchedPrice = matcher.group().replace(",", ".");
                Double price = Double.parseDouble(matchedPrice);
                prices.add(price);
            }
        }
        boolean isSorted = Ordering.natural().reverse().isOrdered(prices);
        Assertions.assertTrue(isSorted, "The parfumes are not ordered by price ascending.");
    }
}
