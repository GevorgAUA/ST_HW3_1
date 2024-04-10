import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class SignIn {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }

    @Test
    @DisplayName("Attempt to Sign In and Verify Failure (Not existing account)")
    public void attemptSignInAndVerifyFailure() {
        driver.get("https://zoomerang.app/login?redirect_url=%2F");

        WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
        emailField.sendKeys("nonexisting@gmail.com");

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys("nonexist");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement failurePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'User not found')]")));

        assertTrue(failurePopup.isDisplayed(), "The 'User not found' pop-up did not appear.");
    }

    @Test
    @DisplayName("Attempt to Sign In and Verify Failure (Wrong Password)")
    public void attemptSignInWithWrongPasswordAndVerify() {
        driver.get("https://zoomerang.app/login?redirect_url=%2F");

        WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
        emailField.sendKeys("gevorgkhachatryan200414@gmail.com");

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys("wrongpassword");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement wrongPasswordPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Wrong password')]")));

        assertTrue(wrongPasswordPopup.isDisplayed(), "The 'Wrong password' pop-up did not appear.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

/* I used lots of waits that were context dependent. either my computer was too slow or the website generation, or it was due to context.
I wanted the test to be as isolated and unrelated as possible, due to that before and after each test the browser is set
to open up and close, I am not sure if that is a correct practice.
The idea is to check 2 scenarios of failed login, one due to non-existing credentials and other due to wrong password.
The code covers testing the failing to log in and the popups.
I believe my code suffered a lot due to website selection.
*/