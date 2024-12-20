package okhttp;

import dto.ContactDtoLombok;
import dto.ResponseMessageDto;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;

public class DeleteContactTests implements BaseApi {

    TokenDto token;
    String contactId;

    @BeforeClass(alwaysRun = true)
    public void loginUser() {
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            token = GSON.fromJson(response.body().string(), TokenDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void addNewContact() {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println("add new contact response code --> " + response.code());
            String message = (GSON.fromJson(response.body().string(), ResponseMessageDto.class)).getMessage();
            System.out.println(message);
            contactId = message.substring(23);
            System.out.println(contactId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(groups = {"smoke", "positive"})
    public void deletePositiveTest() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH + "/" + contactId)
                .addHeader("Authorization", token.getToken())
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void deleteNegativeTest_ExpCode400() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH + "/" + "wrongID")
                .addHeader("Authorization", token.getToken())
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void deleteNegativeTest_ExpCode401_Unauthorized() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_PATH + "/" + contactId)
                .addHeader("Authorization", token.getToken()+"1")
                .delete()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 401);
    }
}
