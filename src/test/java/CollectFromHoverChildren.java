import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CollectFromHoverChildren {
    public static void main(String[] args) {
        WebDriverManager.edgedriver().setup();

        WebDriver driver = new EdgeDriver();
        driver.get("https://zoomerang.app");

        WebElement hoverElement = driver.findElement(By.xpath("//*[contains(text(), 'AI Studio')]"));
        Actions action = new Actions(driver);
        action.moveToElement(hoverElement).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> toolLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, 'aw-[450px]') and contains(@class, 'absolute') and contains(@class, 'z-50') and contains(@class, 'pt-3')]//a[starts-with(@href, '/tools')]")));
        for (WebElement link : toolLinks) {
            System.out.println(link.getAttribute("href"));
        }
        driver.quit();
    }
}

/*I am sorry for the not clean and for complicated code. I used xpath as it seemed to better handle literal code and brackets.
The idea was to hover over the section which will reveal a window with a list of services, I wanted the code to list all links for those,
it searches via href attribute beginning with /tools but as I tested it there were many such elements outside my desired window.
That is why I made it to list ONLY elements that are in that window, which made the code bad.*/