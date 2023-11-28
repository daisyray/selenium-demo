package com.saucedemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class PopUpHandleTest {
    WebDriver webDriver = null;
    @BeforeMethod
    public void beforeMethod() {
        webDriver = new ChromeDriver();
    }
    @Ignore
    public void handleAlert() {
        webDriver.get("https://www.browserstack.com/users/sign_up");
        webDriver.findElement(By.id("user_full_name")).sendKeys("username");
        webDriver.findElement(By.id("user_email_login")).sendKeys("jzc6nu1d@duck.com");
        webDriver.findElement(By.id("user_password")).sendKeys("Test1234*");
        webDriver.findElement(By.id("user_submit")).click();

        WebElement alertBanner = webDriver.findElement(By.id("bs-alert-text-id"));
        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        wait.until(d -> alertBanner.isDisplayed());

        Alert alert = webDriver.switchTo().alert();
        String alertMessage = webDriver.switchTo().alert().getText();
        assertEquals(alertMessage, "Please check the box to confirm acceptance of our ");
        alert.accept();
    }

    @Test
    public void guruAlertTest() {
        webDriver.get("https://demo.guru99.com/test/delete_customer.php");
        webDriver.findElement(By.name("cusid")).sendKeys("53920");
        webDriver.findElement(By.name("submit")).submit();

        Alert alert = webDriver.switchTo().alert();
        String alertText = webDriver.switchTo().alert().getText();
        System.out.println(alertText);
        assertEquals(alertText, "Do you really want to delete this Customer?" );
        alert.accept();
    }

//    @Test
    public void handleMultipleWindow() {
        webDriver.get("https://demo.guru99.com/popup.php");

        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(3000));
        wait.until(driver -> {
            try {
                WebElement orangeBar = webDriver.findElement(By.className("barone"));
                return orangeBar.isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
        });

        String parentWindow = webDriver.getWindowHandle();
        webDriver.findElement(By.linkText("Click Here")).click();

        Set<String> windowsList = webDriver.getWindowHandles();
        for(String s: windowsList) {
            if(!s.equals(parentWindow)) {
                webDriver.switchTo().window(s);
                webDriver.findElement(By.name(("emailid"))).sendKeys("test@test.com");
                webDriver.findElement(By.name("btnLogin")).click();
                break;
            }
        }
    }
    @AfterMethod
    public void closeBrowser() {
        webDriver.quit();
    }
}