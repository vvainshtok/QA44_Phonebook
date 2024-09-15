package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class LoginTests extends ApplicationManager {

    @Test
    public void loginPositiveTest() {
       Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!")
                .clickBtnLoginPositive()
                .isElementContactPresent());
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail@mail.com", "Qwerty123!---")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessageLogin());
    }

    @Test
    public void loginNegativeTest_wrongEmail() {
        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("qa_mail_wrong@mail.com", "Qwerty123!")
                .clickBtnLoginNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessageLogin());
    }

}
