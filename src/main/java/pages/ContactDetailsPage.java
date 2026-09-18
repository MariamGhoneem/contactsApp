package pages;

import base.PageBase;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import models.Contact;
import org.openqa.selenium.By;

public class ContactDetailsPage extends PageBase {

    public ContactDetailsPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks whether the saved contact's full name is displayed on this screen.
     */
    @Step("Verify contact name is displayed")
    public boolean isContactDisplayed(Contact contact) {
        String fullName = contact.getFirstName() + " " + contact.getLastName();
        By nameLocator = AppiumBy.androidUIAutomator("new UiSelector().text(\"" + fullName + "\")");
        return isDisplayed(nameLocator);
    }
}
