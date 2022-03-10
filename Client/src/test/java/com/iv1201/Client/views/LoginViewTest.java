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
        WebElement resetPwButton = driver.findElement(By.id("resetBtn"));
        resetPwButton.click();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Reset Account", pageTitle);
    }
    
    /**
     * Test of login button, of view login. No input.
     */
    @Test
    public void testLoginBtnNoInput() {
        WebElement loginBtn = driver.findElement(By.id("loginBtn"));
        loginBtn.click();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Login", pageTitle);
    }
    
    /**
     * Test of login button, of view login. Wrong input.
     */
    @Test
    public void testLoginBtnWrongInput() {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("wrongUsername");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrongPassword");
        WebElement loginBtn = driver.findElement(By.id("loginBtn"));
        loginBtn.click();
        WebElement errorMsg = driver.findElement(By.className("error"));
        Assert.assertEquals("Invalid username or password", errorMsg.getText());
    }
    
    /**
     * Test of login button, of view login. Correct input recruiter.
     */
    @Test
    public void testLoginBtnCorrectInputRecruiter() {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("JoelleWilkinson");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("LiZ98qvL8Lw");
        WebElement loginBtn = driver.findElement(By.id("loginBtn"));
        loginBtn.click();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Recruiter", pageTitle);
    }
    
    /**
     * Test of login button, of view login. Correct input applicant.
     */
    @Test
    public void testLoginBtnCorrectInputApplicant() {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("LeroyCrane");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("1234");
        WebElement loginBtn = driver.findElement(By.id("loginBtn"));
        loginBtn.click();
        String pageTitle = driver.getTitle();
        Assert.assertEquals("Application", pageTitle);
    }
    
    /**
     * Test of username field, of view login.
     */
    @Test
    public void testUsernameField() {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("username");
        String writtenUsername = usernameField.getAttribute("value");
        Assert.assertEquals("username", writtenUsername);
    }
    
    /**
     * Test of password field, of view login.
     */
    @Test
    public void testPasswordField() {
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("password");
        String writtenPassword = passwordField.getAttribute("value");
        Assert.assertEquals("password", writtenPassword);
    }
}
