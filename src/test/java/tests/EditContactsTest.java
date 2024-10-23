package tests;

import data_provider.DPAddContact;
import dto.ContactDtoLombok;
import dto.UserDto;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
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
import utils.RetryAnalyzer;
import utils.TestNGListener;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;
import static utils.PropertiesReader.getProperty;

@Listeners(TestNGListener.class)

public class EditContactsTest extends ApplicationManager {

    //UserDto user = new UserDto("vv17@gmail.com","QWErty123!");
    UserDto user = new UserDto(getProperty("data.properties","email"),
            getProperty("data.properties","password"));

    AddPage addPage;

    @BeforeMethod
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
    }

    @Description("positive test edit first contact all fields")
    @Owner("Victor")
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void EditFirstContactPositiveTest_allFields() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactPage contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        Allure.step("edit first contact");
        contactPage.editFirstInList();
        String cardBefore = contactPage.getContactCard();
        contactPage.clickBtnEditContact()
                .fillEditContactForm(contact)
                .clickBtnSave()
                .pause(3);
        String cardAfter = contactPage.getContactCard();
        Assert.assertNotEquals(cardBefore, cardAfter);
        //  Assert.assertTrue(contactPage.isFirstPhoneEquals(phone)); // второй вариант проверки
    }

    @Test
    public void EditFirstContactPositiveTest_changePhone() {
        String phone = generatePhone(10);
        ContactPage contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        contactPage.editFirstInList();
        String cardBefore = contactPage.getContactCard();
        contactPage.clickBtnEditContact()
                .changePhone(phone)
                .clickBtnSave()
                .pause(3);
        String cardAfter = contactPage.getContactCard();
        Assert.assertNotEquals(cardBefore, cardAfter);
    }

    @Test(dataProvider = "editContactDPFile_wrongOrEmptyFields", dataProviderClass = DPAddContact.class)
    public void editContactNegativeTest_wrongOrEmptyFields(ContactDtoLombok contact) {
        ContactPage contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        contactPage.editFirstInList();
        String cardBefore = contactPage.getContactCard();
        contactPage.clickBtnEditContact()
                .clickBtnSave()
                .pause(3);
        String cardAfter = contactPage.getContactCard();
        Assert.assertEquals(cardBefore, cardAfter);
    }

    @Test
    public void printTest() {
        ContactPage contactPage = clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        String cardText = contactPage.editFirstInList().getContactCard();
        System.out.println(cardText);
    }
}
