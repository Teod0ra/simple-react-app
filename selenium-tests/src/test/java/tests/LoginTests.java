package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LibraryPage;
import pages.LoginPage;
import utils.TestData;
import utils.WebDriverSetup;

import java.time.Duration;

public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private LibraryPage libraryPage;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = WebDriverSetup.getDriver();
        driver.get("http://localhost:3000");  // Your React app URL
        loginPage = new LoginPage(driver);
        libraryPage = new LibraryPage(driver);
    }

    @Test
    public void testValidLogin() {
        loginPage.loginAs("admin", "1234");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertEquals(libraryPage.getLibraryAppHeaderText(), TestData.LIBRARY_APP_HEADER);
    }

    @Test
    public void testInvalidLogin() {
        loginPage.loginAs("admin", "wrong");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertEquals(loginPage.getErrorMessage(), TestData.LOGIN_ERROR_MESSAGE);
    }

    @Test
    public void createBookAndCheckIfVisible() {
        loginPage.loginAs("admin", "wrong");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }


    @AfterClass
    public void teardown() {
        WebDriverSetup.quitDriver();
    }
}
