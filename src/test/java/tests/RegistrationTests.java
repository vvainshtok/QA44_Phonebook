package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class RegistrationTests extends ApplicationManager {

    @Test
    public void registrationPositiveTest() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("vv_qa_email@mail.com", "Password123_!")
                .clickBtnRegistrationPositive()
                .isElementContactPresent());
    }

    @Test
    public void registrationNegativeTest_wrongEmail() {

        Assert.assertTrue(new HomePage(getDriver())
                .clickBtnLoginHeader()
                .typeLoginForm("invalidemail", "Password123_!")
                .clickBtnRegistrationNegative()
                .closeAlert()
                .isTextInElementPresent_errorMessageRegistration());
    }
}
