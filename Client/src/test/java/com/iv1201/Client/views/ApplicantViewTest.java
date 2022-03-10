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
public class ApplicantViewTest {
    
    //A driver object referencing WebDriver 
    WebDriver driver;
    
    public ApplicantViewTest() {
        //Setting the webdriver.chrome.driver property to its executable's location
        System.setProperty("webdriver.chrome.driver", "C:\\selenium-webdrivers\\chromedriver.exe");
        //Instantiating driver 
        driver = new ChromeDriver();
        //Set implicit wait of 10 seconds
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Launch page
        driver.get("http://localhost:8080/login");
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("LeroyCrane");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("1234");
        WebElement loginBtn = driver.findElement(By.id("loginBtn"));
        loginBtn.click();
    }
    
    @Before
    public void setUp() {
        //Launch page
        driver.get("http://localhost:8080/startpage");
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
    
    /**
     * Test of experience field, of view applicant.
     */
    @Test
    public void testCompetenceFieldDefault() {
        WebElement competenceField = driver.findElement(By.id("competence"));
        String chosenCompetence = competenceField.getAttribute("value");
        Assert.assertEquals("1", chosenCompetence);
    }
    
    /**
     * Test of experience field, of view applicant.
     */
    @Test
    public void testCompetenceField() {
        WebElement competenceField = driver.findElement(By.id("competence"));
        Select dropDownCompetence = new Select(competenceField);
        dropDownCompetence.selectByIndex(1);
        String chosenCompetence = competenceField.getAttribute("value");
        Assert.assertEquals("2", chosenCompetence);
    }
    
    /**
     * Test of experience field, of view applicant.
     */
    @Test
    public void testExperienceField() {
        WebElement experienceField = driver.findElement(By.id("experience"));
        experienceField.sendKeys("experience");
        String writtenExperience = experienceField.getAttribute("value");
        Assert.assertEquals("experience", writtenExperience);
    }
    
    /**
     * Test of begin time period field, of view applicant.
     */
    @Test
    public void testBeginTimePeriodFieldDefault() {
        WebElement timeField = driver.findElement(By.id("start"));
        String chosenDate = timeField.getAttribute("value");
        Assert.assertEquals("2022-06-01", chosenDate);
    }
    
    /**
     * Test of begin time period field, of view applicant.
     */
    @Test
    public void testBeginTimePeriodField() {
        WebElement timeField = driver.findElement(By.id("start"));
        timeField.sendKeys("2022-07-12");
        String chosenDate = timeField.getAttribute("value");
        Assert.assertEquals("2022-02-12", chosenDate);
    }
    
    /**
     * Test of end time period field, of view applicant.
     */
    @Test
    public void testEndTimePeriodFieldDefault() {
        WebElement timeField = driver.findElement(By.id("end"));
        String chosenDate = timeField.getAttribute("value");
        Assert.assertEquals("2022-07-01", chosenDate);
    }
    
    /**
     * Test of end time period field, of view applicant.
     */
    @Test
    public void testEndTimePeriodField() {
        WebElement timeField = driver.findElement(By.id("end"));
        timeField.sendKeys("2022-07-19");
        String chosenDate = timeField.getAttribute("value");
        Assert.assertEquals("2022-02-19", chosenDate);
    }
}