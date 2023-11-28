package com.saucedemo;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class LoginPageTest {
    WebDriver webDriver = null;
    @BeforeMethod
    public void loginTest() {
        webDriver = new ChromeDriver();
        webDriver.get("https://www.saucedemo.com/");
        String title = webDriver.getTitle();
        assertEquals(title, "Swag Labs");
    }

    @Ignore
    public void checkInvalidPasswordErrorMsg() {
        webDriver.findElement(By.id("user-name")).sendKeys("standard_user");
        webDriver.findElement(By.id("password")).sendKeys("invalid");
        webDriver.findElement(By.id("login-button")).click();
        String errorMsg = webDriver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")).getText();
        assertEquals(errorMsg, "Epic sadface: Username and password do not match any user in this service");
    }

    @Ignore // check black box usernames
    public void checkAcceptedUsername() {
        String userNames = webDriver.findElement(By.id("login_credentials")).getText();
        assertEquals(userNames, "Accepted usernames are:\n" +
                "standard_user\n" +
                "locked_out_user\n" +
                "problem_user\n" +
                "performance_glitch_user\n" +
                "error_user\n" +
                "visual_user");
    }
    @Ignore
    public void userAbleToLogin() {
        webDriver.findElement(By.id("user-name")).sendKeys("standard_user");
        webDriver.findElement(By.id("password")).sendKeys("secret_sauce");
        webDriver.findElement(By.id("login-button")).click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String inventoryPagetitle = webDriver.getTitle();
        assertEquals(inventoryPagetitle, "Swag Labs");
    }

    @Ignore
    public void checkForInventoryCards() {
        this.userAbleToLogin();
        Integer countCard = webDriver.findElements(By.className("inventory_item")).size();
        assertEquals(countCard, 6);
    }

    @Ignore
    public void addAllItemsToCart() throws InterruptedException {
        this.userAbleToLogin();
        List<WebElement> addToCart = webDriver.findElements(By.className("btn_inventory"));
       for(WebElement e: addToCart) {
           e.click();
        }
        String shoppingCartBadge = webDriver.findElement(By.className("shopping_cart_badge")).getText();
        assertEquals(shoppingCartBadge, "6");
    }

    @AfterMethod
    public void closeBrowser() {
        webDriver.close();
    }

}
