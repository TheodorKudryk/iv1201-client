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
public class ResetAccountViewTest {
    
    //A driver object referencing WebDriver 
    WebDriver driver;
    
    public ResetAccountViewTest() {
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
        driver.get("http://localhost:8080/resetAccount");
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
     * Test of update button, of view resetAccount. Invalid email.
     */
    @Test
    public void testUpdateBtnInvalidEmail() {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("invalidEmail");
        WebElement updateBtn = driver.findElement(By.id("updateBtn"));
        updateBtn.click();
        WebElement errorMsg = driver.findElement(By.className("error"));
        Assert.assertEquals("No such email found", errorMsg.getText());
    }
    
    /**
     * Test of update button, of view resetAccount. Account already reset.
     */
    @Test
    public void testUpdateBtnAccountAlreadyReset() {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("l_crane118@finnsinte.se");
        WebElement updateBtn = driver.findElement(By.id("updateBtn"));
        updateBtn.click();
        WebElement errorMsg = driver.findElement(By.className("error"));
        Assert.assertEquals("Account has already been reset", errorMsg.getText());
    }
    
    /**
     * Test of update button, of view resetAccount. Link sent
     */
    @Test
    public void testUpdateBtnLinkSent() {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("wolf_arsenio@finnsinte.se");
        WebElement updateBtn = driver.findElement(By.id("updateBtn"));
        updateBtn.click();
        WebElement errorMsg = driver.findElement(By.className("error"));
        Assert.assertEquals("Link has been sent to email", errorMsg.getText());
    }
    
    /**
     * Test of email field, of view resetAccount.
     */
    @Test
    public void testEmailField() {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("email");
        String writtenEmail = emailField.getAttribute("value");
        Assert.assertEquals("email", writtenEmail);
    }
}