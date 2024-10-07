package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;
import java.time.Duration;

public class BasePage {

    static WebDriver driver;
    static Logger logger = LoggerFactory.getLogger(BasePage.class);

    public static void setDriver(WebDriver wd) {
        driver = wd;
    }

    public void pause(int time) {
        try {
            Thread.sleep(time*1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isElementPresent(WebElement element, String text) {
        return element.getText().contains(text);
    }

    public static <T extends BasePage> T clickButtonsOnHeader(HeaderMenuItem headerMenuItem) {
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(headerMenuItem.getLocator())));
            element.click();
        }catch (TimeoutException exception) {
            exception.printStackTrace();
            System.out.println("created exception");
        }


        switch (headerMenuItem) {
            case HOME -> {
                return (T) new HomePage(driver);
            }
            case ABOUT -> {
                return (T) new AboutPage(driver);
            }
            case LOGIN -> {
                return (T) new LoginPage(driver);
            }
            case ADD -> {
                return (T) new AddPage(driver);
            }
            case CONTACTS -> {
                return (T) new ContactPage(driver);
            }
            default -> {
                throw new IllegalArgumentException("Invalid parameter header menu");
            }
        }
    }

    public boolean isUrlContains(String urlPart, int time) {
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.urlContains(urlPart));
    }
}
