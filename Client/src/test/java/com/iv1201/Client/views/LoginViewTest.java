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

/**
 *
 * @author leohj
 */
public class LoginViewTest {
    
    //A driver object referencing WebDriver 
    WebDriver driver;
    
    public LoginViewTest() {
        //Setting the webdriver.chrome.driver property to its executable's location
        System.setProperty("webdriver.chrome.driver", "C:\\selenium-webdrivers\\chromedriver.exe");
    }
    
    @Before
    public void setUp() {
        //Instantiating driver 
        driver = new ChromeDriver();
        //Set implicit wait of 10 seconds
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Launch page
        driver.get("http://localhost:8080/login");
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
    
    /**
     * Test of resetPassword button, of view login.
     */
    @Test
    public void testResetBtn() {
        //Click on resetPasswordButton
        WebElement resetPwButton = driver.findElement(By.id("resetBtn"));
        resetPwButton.click();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Login Details", pageTitle);
    }
    
    /**
     * Test of username field, of view login.
     */
    @Test
    public void testUsernameField() {
        //Click on resetPasswordButton
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("username");
        String writtenUsername = usernameField.getAttribute("value");
        Assert.assertEquals("username", writtenUsername);
    }
    
}
