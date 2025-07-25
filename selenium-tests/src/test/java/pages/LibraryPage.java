package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestData;

import java.time.Duration;


public class LibraryPage {
    private final WebDriver driver;
    private final static String XPATH_EDIT_BOOK_ITEM = "//ul[@class='book-list']/li[strong[text()='%s']]/div/button[@data-id='editBook']";
    private final static String XPATH_DELETE_BOOK_ITEM = "//ul[@class='book-list']/li[strong[text()='%s']]/div/button[@data-id='deleteBook']";
    private final By bookTitleInput = By.cssSelector("input[data-id='bookTitle']");
    private final By bookAuthorInput = By.cssSelector("input[data-id='bookAuthor']");
    private final By submitButton = By.cssSelector("button[data-id='addUpdateBook']");
    private final By editBookButton = By.xpath(String.format(XPATH_EDIT_BOOK_ITEM, TestData.BOOK_LOTR_TITLE));
    private final By deleteBookButton = By.xpath(String.format(XPATH_DELETE_BOOK_ITEM, TestData.BOOK_DELETED_TITLE));
    private final By libraryAppHeader = By.cssSelector("h1");

    public LibraryPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getLibraryAppHeader() {
        return libraryAppHeader;
    }
    public String getLibraryAppHeaderText() {
        return driver.findElement(libraryAppHeader).getText();
    }
    public By getBookAuthorInput() {
        return bookAuthorInput;
    }

    public void addBook(String title, String author) {
        driver.findElement(bookTitleInput).clear();
        driver.findElement(bookTitleInput).sendKeys(title);
        driver.findElement(bookAuthorInput).clear();
        driver.findElement(bookAuthorInput).sendKeys(author);
        driver.findElement(submitButton).click();
    }

    public void editBook(String title, String author) {
        addBook(TestData.BOOK_LOTR_TITLE, TestData.BOOK_LOTR_AUTHOR);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(editBookButton));
        driver.findElement(editBookButton).click();
        driver.findElement(bookTitleInput).clear();
        driver.findElement(bookTitleInput).sendKeys(title);
        driver.findElement(bookAuthorInput).clear();
        driver.findElement(bookAuthorInput).sendKeys(author);
        driver.findElement(submitButton).click();
    }
    public void deleteBook(String title) {
        addBook(TestData.BOOK_DELETED_TITLE, TestData.BOOK_DELETED_AUTHOR);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteBookButton));
        driver.findElement(deleteBookButton).click();
    }


}
