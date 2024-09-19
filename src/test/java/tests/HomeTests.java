package tests;

import manager.ApplicationManager;
import org.testng.annotations.Test;
import pages.AboutPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;

public class HomeTests extends ApplicationManager {

    @Test
    public void firstTest() {
        HomePage homepage = new HomePage(getDriver());
    }

    @Test
    public void testHeaderMenu() {
        HomePage homePage = new HomePage(getDriver());
        homePage.pause(2);
        AboutPage aboutPage = clickButtonsOnHeader(HeaderMenuItem.ABOUT);
        aboutPage.pause(2);
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.pause(2);
        homePage = clickButtonsOnHeader(HeaderMenuItem.HOME);
        homePage.pause(2);
    }
}






