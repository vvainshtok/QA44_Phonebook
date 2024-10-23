package pages;

import dto.ContactDtoLombok;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class ContactPage extends BasePage{

    public ContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContact;

    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM'][last()]/h3")
    WebElement lastPhoneInList;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']//div[@class='contact-item_card__2SOIM']/h3")
    WebElement firstPhoneInList;

    @FindBy(xpath = "//button[text()='Remove']")
    WebElement btnRemove;
    @FindBy(xpath = "//button[text()='Edit']")
    WebElement btnEdit;

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;
    @FindBy(xpath = "//input[@placeholder='desc']")
    WebElement inputDescription;

    @FindBy(xpath = "//div[@class='form_form__FOqHs']/button")
    WebElement btnSave;

    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement contactCard;

    //--------------------------------- на уроке 08.10.24
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/h2")
    WebElement contactCardNameLastName;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement contactCardPhone;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/br")
    WebElement contactCardEmail;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/br[last()]")
    WebElement contactCardAddress;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']/h3")
    WebElement contactCardDescription;
    //---------------------------------



    public boolean isElementContactPresent() {
        return btnContact.isDisplayed();
    }

    public boolean isLastPhoneEquals(String phone) {
        return lastPhoneInList.getText().equals(phone);
    }

    public boolean isFirstPhoneEquals(String phone) {
        return firstPhoneInList.getText().equals(phone);
    }

    public boolean urContainsAdd() {
        return isUrlContains("add", 5);
    }

    public boolean isAlertPresent(int time) {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(time))
                    .until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            alert.accept();
            return true;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ContactPage editLastInList() {
        lastPhoneInList.click();
        return this;
    }

    public ContactPage editFirstInList() {
        firstPhoneInList.click();
        return this;
    }


    public ContactPage removeContact() {
        btnRemove.click();
        return this;
    }


    public int getContactsNumber() {
        return driver.findElements(By.xpath("//*[@class='contact-item_card__2SOIM']")).size();
        }

    public ContactPage clickBtnEditContact() {
        btnEdit.click();
        return this;
    }

    public ContactPage fillEditContactForm(ContactDtoLombok contact) {
        inputName.clear();
        inputName.sendKeys(contact.getName());
        inputLastName.clear();
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.clear();
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.clear();
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.clear();
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.clear();
        inputDescription.sendKeys(contact.getDescription());
        return this;
    }

    public ContactPage changePhone(String phone) {
        inputPhone.clear();
        inputPhone.sendKeys(phone);
        return this;
    }

    public ContactPage clickBtnSave() {
        btnSave.click();
        return this;
    }

    public String getContactCard() {
        return((contactCard.getText()));
    }
}
