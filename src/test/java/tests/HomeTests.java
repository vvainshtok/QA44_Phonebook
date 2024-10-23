package tests;

import manager.ApplicationManager;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AboutPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static pages.BasePage.clickButtonsOnHeader;

@Listeners(TestNGListener.class)

public class HomeTests extends ApplicationManager {

    @Test
    public void firstTest() {
        HomePage homepage = new HomePage(getDriver());
    }

    @Test
    public void testHeaderMenu() {
        HomePage homePage = new HomePage(getDriver());
        AboutPage aboutPage = clickButtonsOnHeader(HeaderMenuItem.ABOUT);
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        homePage = clickButtonsOnHeader(HeaderMenuItem.HOME);
    }
}






