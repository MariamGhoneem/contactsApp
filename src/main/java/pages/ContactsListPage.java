package pages;

import base.PageBase;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ContactsListPage extends PageBase {

    private static final By ADD_CONTACT_BUTTON = By.id("com.google.android.contacts:id/floating_action_button");

    public ContactsListPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Tap the add contact button")
    public CreateContactPage tapAddContact() {
        click(ADD_CONTACT_BUTTON);
        return new CreateContactPage(driver);
    }
}
