package pages;

import base.PageBase;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import models.Contact;
import org.openqa.selenium.By;

public class CreateContactPage extends PageBase {

    private static final By FIRST_NAME_FIELD = AppiumBy.androidUIAutomator("new UiSelector().text(\"First name\")");
    private static final By LAST_NAME_FIELD = AppiumBy.androidUIAutomator("new UiSelector().text(\"Last name\")");
    private static final By PHONE_FIELD = AppiumBy.androidUIAutomator("new UiSelector().text(\"+20\")");
    private static final By SAVE_BUTTON = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(9)");

    public CreateContactPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Enter first name")
    public CreateContactPage enterFirstName(String firstName) {
        type(FIRST_NAME_FIELD, firstName);
        return this;
    }

    @Step("Enter last name")
    public CreateContactPage enterLastName(String lastName) {
        type(LAST_NAME_FIELD, lastName);
        return this;
    }

    @Step("Enter phone number")
    public CreateContactPage enterPhoneNumber(String phoneNumber) {
        type(PHONE_FIELD, "+20" + phoneNumber);
        return this;
    }

    @Step("Save the contact")
    public ContactDetailsPage save() {
        click(SAVE_BUTTON);
        return new ContactDetailsPage(driver);
    }


    @Step("Fill in contact details and save")
    public ContactDetailsPage fillAndSave(Contact contact) {
        enterFirstName(contact.getFirstName());
        enterLastName(contact.getLastName());
        enterPhoneNumber(contact.getPhoneNumber());
        return save();
    }
}
