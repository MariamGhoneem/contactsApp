package pages;

import base.PageBase;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import models.Contact;
import org.openqa.selenium.By;

public class ContactsListPage extends PageBase {

    private static final By ADD_CONTACT_BUTTON = By.id("com.google.android.contacts:id/floating_action_button");
    private static final int MAX_SCROLL_ATTEMPTS = 5;

    public ContactsListPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Tap the add contact button")
    public CreateContactPage tapAddContact() {
        click(ADD_CONTACT_BUTTON);
        return new CreateContactPage(driver);
    }

    /**
     * Scrolls down the contacts list, up to {@value #MAX_SCROLL_ATTEMPTS} times,
     * until the given contact's full name becomes visible.
     */
    @Step("Scroll until the saved contact is visible in the list")
    public boolean isContactDisplayed(Contact contact) {
        String fullName = contact.getFirstName() + " " + contact.getLastName();
        By nameLocator = AppiumBy.androidUIAutomator("new UiSelector().text(\"" + fullName + "\")");
        for (int attempt = 0; attempt < MAX_SCROLL_ATTEMPTS && !isDisplayed(nameLocator); attempt++) {
            scrollDown();
        }
        return isDisplayed(nameLocator);
    }
}
