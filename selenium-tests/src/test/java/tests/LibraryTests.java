package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class LibraryTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private LibraryPage libraryPage;
    private WebDriverWait wait;
    private static final String XPATH_BOOK_TITLE = "//ul[@class='book-list']/li[strong[text()='%s']]";

    @BeforeClass
    public void setup() {
        driver = WebDriverSetup.getDriver();
        driver.get("http://localhost:3000");  // Your React app URL
        loginPage = new LoginPage(driver);
        libraryPage = new LibraryPage(driver);
    }

    @Test
    public void createBookAndCheckIfVisible() {
        authenticate();
        libraryPage.addBook(TestData.BOOK_LOTR_TITLE, TestData.BOOK_LOTR_AUTHOR);
        WebElement newBook = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(XPATH_BOOK_TITLE, TestData.BOOK_LOTR_TITLE))
        ));
        Assert.assertTrue(newBook.isDisplayed());
    }

    @Test
    public void editBookAndCheckCorrectData() {
        authenticate();
        libraryPage.editBook(TestData.BOOK_UPDATED_TITLE, TestData.BOOK_UPDATED_AUTHOR);
        WebElement newBook = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(XPATH_BOOK_TITLE, TestData.BOOK_UPDATED_TITLE))
        ));
        Assert.assertTrue(newBook.isDisplayed());
    }

    @Test
    public void deleteBookAndCheckDeletion() {
        authenticate();
        libraryPage.deleteBook(TestData.BOOK_LOTR_TITLE);
        Boolean deletedBook = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath(String.format(XPATH_BOOK_TITLE, TestData.BOOK_LOTR_TITLE))
        ));
        Assert.assertTrue(deletedBook);
    }

    private void authenticate() {
        loginPage.loginAs("admin", "1234");
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(libraryPage.getBookAuthorInput()));
    }

    @AfterClass
    public void teardown() {
        WebDriverSetup.quitDriver();
    }
}
