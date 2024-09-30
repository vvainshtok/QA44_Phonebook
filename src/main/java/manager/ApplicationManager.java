package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class ApplicationManager {

    private WebDriver driver;
    public WebDriver getDriver() {
        return driver;
    }

    public Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Start method --> setUp" );
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Start method --> tearDown" );
    //       if(driver != null) {
    //       driver.quit();
    //     }
    }
}
