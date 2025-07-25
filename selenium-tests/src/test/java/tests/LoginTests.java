package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
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

    @Test ( priority = 2 )
    public void testValidLogin() {
        authenticate();
        wait.until(ExpectedConditions.visibilityOfElementLocated(libraryPage.getLibraryAppHeader()));
        Assert.assertEquals(libraryPage.getLibraryAppHeaderText(), TestData.LIBRARY_APP_HEADER);
    }

    @Test ( priority = 1 )
    public void testInvalidLogin() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        loginPage.loginAs("admin", "wrong",wait);

        Assert.assertEquals(loginPage.getErrorMessage(wait), TestData.LOGIN_ERROR_MESSAGE);
    }

    private void authenticate() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        loginPage.loginAs("admin", "1234",wait);

    }


    @AfterClass
    public void teardown() {
        WebDriverSetup.quitDriver();
    }
}
