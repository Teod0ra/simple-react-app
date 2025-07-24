package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LibraryPage {
    private WebDriver driver;

    private By bookTitleInput = By.cssSelector("input[placeholder='Book Title']");
    private By bookAuthorInput = By.cssSelector("input[placeholder='Author']");
    private By submitButton = By.cssSelector("button");
    private By libraryAppHeader = By.cssSelector("h1");

    public LibraryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getLibraryAppHeaderText() {
        return driver.findElement(libraryAppHeader).getText();
    }

    public void addBook(String title, String author) {
        driver.findElement(bookTitleInput).clear();
        driver.findElement(bookTitleInput).sendKeys(title);
        driver.findElement(bookAuthorInput).clear();
        driver.findElement(bookAuthorInput).sendKeys(author);
        driver.findElement(submitButton).click();
    }

}
