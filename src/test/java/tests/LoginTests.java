package tests;

import data_provider.DPLogin;
import dto.UserDto;

import manager.ApplicationManager;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.TestNGListener;

import static utils.TakeScreenshot.takeScreenshot;

@Listeners(TestNGListener.class)

public class LoginTests extends ApplicationManager {

    @Test
    public void loginPositiveTest() {
        boolean result = new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!")
                .clickBtnLoginPositive()
                .isElementContactPresent();
        takeScreenshot((TakesScreenshot) getDriver());
        Assert.assertTrue(result);
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!---")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage());
    }

    @Test
    public void loginNegativeTest_wrongEmail() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail_wrong@mail.com", "Qwerty123!")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage());
    }

    // домашнее задание №8
    @Test(dataProvider = "addUserFile", dataProviderClass = DPLogin.class)
    public void loginNegativeTestDP(UserDto user) {
        Assert.assertTrue(
                new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm(user)
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessage())
        ;
    }

}
