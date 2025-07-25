package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;

    private final By usernameInput = By.cssSelector("input[placeholder='Username']");
    private final By passwordInput = By.cssSelector("input[placeholder='Password']");
    private final By loginButton = By.cssSelector("button");
    private final By errorMessage = By.className("error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getUsernameInput() {
        return usernameInput;
    }

    public boolean usernameExists() {
        return !driver.findElements(usernameInput).isEmpty();
    }

    public void enterUsername(String username) {
        driver.findElement(getUsernameInput()).clear();
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }

    public void loginAs(String username, String password, WebDriverWait wait) {
        if (usernameExists()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(getUsernameInput()));
            enterUsername(username);
            enterPassword(password);
            clickLogin();
        }
    }
}
