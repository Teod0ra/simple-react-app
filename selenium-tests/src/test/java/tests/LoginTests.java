package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.WebDriverSetup;

public class LoginTests {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass
    public void setup() {
        driver = WebDriverSetup.getDriver();
        driver.get("http://localhost:3000");  // Your React app URL
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testValidLogin() {
        loginPage.loginAs("admin", "1234");
        // Add assertions here to verify login success (e.g., presence of book list)
    }

    @AfterClass
    public void teardown() {
        WebDriverSetup.quitDriver();
    }
}
