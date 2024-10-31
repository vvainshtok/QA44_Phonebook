package tests;

import data_provider.DPAddContact;
import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.TokenDto;
import okhttp.GetAllContactsTests;
import dto.UserDto;
import manager.ApplicationManager;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListener;

import java.io.IOException;

import static interfaces.BaseApi.*;
import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;

@Listeners(TestNGListener.class)

public class AddContactsTests extends ApplicationManager {

    UserDto user = new UserDto("vv17@gmail.com","QWErty123!");
    AddPage addPage;
    TokenDto token;

    @BeforeMethod(alwaysRun = true)
    public void login() {
        new HomePage(getDriver());
        LoginPage loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginForm(user).clickBtnLoginPositive();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);

        // added : homework 30.10.24
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            token = GSON.fromJson(response.body().string(), TokenDto.class);} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(groups = {"smoke"})
    public void addNewContactPositiveTest() throws IOException {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        System.out.println("contact --> " + contact);
        addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isLastPhoneEquals(contact.getPhone());

        // added : homework 30.10.24
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .get()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContactsDto contacts = new ContactsDto();
        boolean isAdded = false;
        if (response.isSuccessful()) {
            contacts = GSON.fromJson(response.body().string(), ContactsDto.class);
            for (ContactDtoLombok c : contacts.getContacts())
                if (c.equals(contact)) isAdded = true;
        }
        else
            System.out.println("Something went wrong");

        Assert.assertTrue(isAdded);
    }

    @Test
    public void addNewContactNegativeTest_wrongEmailWOAt() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .closeAlert()
                .contains("Email not valid"));
    }

    // домашнее задание № 7
    @Test
    public void addNewContactNegativeTest_wrongPhoneNonDigits() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generateString(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .closeAlert()
                .contains("Phone not valid"));
    }

    @Test
    public void addNewContactNegativeTest_wrongPhoneLessThan10() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(9))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .closeAlert()
                .contains("Phone not valid"));
    }

    @Test
    public void addNewContactNegativeTest_wrongPhoneMoreThan15() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(16))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .closeAlert()
                .contains("Phone not valid"));
    }


    @Test
    public void addNewContactNegativeTest_emptyName() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("")
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isBtnSavePresent());
    }

    @Test
    public void addNewContactNegativeTest_emptyLastName() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName("")
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isBtnSavePresent());
    }

    @Test
    public void addNewContactNegativeTest_emptyAddress() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address("")
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isBtnSavePresent());
    }

    // на уроке 24.09.2024
    @Test
    public void addNewContactNegativeTest_NameIsEmpty() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("")
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .urContainsAdd());
    }

    @Test
    public void addNewContactNegativeTest_wrongEmail() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(10))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateString(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5));
    }

    @Test(dataProvider = "addNewContactDP", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongEmailDP(ContactDtoLombok contact) {
        System.out.println("-->" + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5));
    }

    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_wrongEmailDPFile(ContactDtoLombok contact) {
        System.out.println("-->" + contact);
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactPositive()
                .isAlertPresent(5));
    }

    // домашнее задание №8
    @Test(dataProvider = "addNewContactDPFile_EmptyFields", dataProviderClass = DPAddContact.class)
    public void addNewContactNegativeTest_emptyFieldDP(ContactDtoLombok contact) {
        Assert.assertTrue(addPage.fillContactForm(contact)
                .clickBtnSaveContactNegative()
                .isBtnSavePresent());
    }

}
