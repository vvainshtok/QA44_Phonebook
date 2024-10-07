package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

@Listeners(TestNGListener.class)

public class DeleteContactsTest extends ApplicationManager {

    UserDto user = new UserDto("vv17@gmail.com","QWErty123!");
    AddPage addPage;

    @BeforeMethod
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        }

    // домашнее задание №8
    @Test
    public void DeleteLastContactPositiveTest() {
        ContactPage contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        contactPage.pause(2);
        int numberBefore = contactPage.getContactsNumber();
        contactPage.editLastInList().removeContact().pause(2);
        int numberAfter = contactPage.getContactsNumber();
        Assert.assertEquals(numberBefore-numberAfter, 1);
    }

    @Test
    public void DeleteContactAfterAddPositiveTest() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactPage contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        contactPage.pause(2);
        int numberBefore = contactPage.getContactsNumber();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
        contactPage = addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .editLastInList().removeContact();
        addPage.pause(2);
        int numberAfter = contactPage.getContactsNumber();
        Assert.assertEquals(numberBefore-numberAfter, 0);

    }


}
