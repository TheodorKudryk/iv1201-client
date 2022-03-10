package com.iv1201.Client.views;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author leohj
 */
public class UserUpdateViewTest {
    
    //A driver object referencing WebDriver 
    WebDriver driver;
    
    public UserUpdateViewTest() {
        //Setting the webdriver.chrome.driver property to its executable's location
        System.setProperty("webdriver.chrome.driver", "C:\\selenium-webdrivers\\chromedriver.exe");
        //Instantiating driver 
        driver = new ChromeDriver();
        //Set implicit wait of 10 seconds
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Before
    public void setUp() {
        //Launch page
        driver.get("http://localhost:8080/userUpdate?token=aca4fca9-0267-4b27-b7d5-b796ede98839");
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
    
    /**
     * Test of testBackToLogin button, of view resetAccount.
     */
    @Test
    public void testBackToLoginBtn() {
        WebElement backToLoginBtn = driver.findElement(By.id("backToLoginBtn"));
        backToLoginBtn.click();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Login", pageTitle);
    }
    
    /**
     * Test of update button, of view user update. No input.
     */
    @Test
    public void testUpdateBtnNoInput() {
        WebElement updateBtn = driver.findElement(By.id("updateBtn"));
        updateBtn.click();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Reset", pageTitle);
    }
    
    /**
     * Test of update button, of view user update. Correct input applicant.
     */
    /*
    @Test
    public void testUpdateBtn() {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("username");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("password");
        WebElement updateBtn = driver.findElement(By.id("updateBtn"));
        updateBtn.click();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Login", pageTitle);
    }
    */
    
    /**
     * Test of username field, of view user update.
     */
    @Test
    public void testUsernameField() {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("username");
        String writtenUsername = usernameField.getAttribute("value");
        Assert.assertEquals("username", writtenUsername);
    }
    
    /**
     * Test of password field, of view user update.
     */
    @Test
    public void testPasswordField() {
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("password");
        String writtenPassword = passwordField.getAttribute("value");
        Assert.assertEquals("password", writtenPassword);
    }
}
