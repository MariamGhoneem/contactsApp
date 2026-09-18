package tests;

import base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import models.Contact;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactDetailsPage;
import pages.ContactsListPage;
import utils.ContactDataGenerator;

@Epic("Contacts")
@Feature("Create Contact")
public class CreateContactTest extends TestBase {

    @Test
    @Story("Create contact with valid data")
    @Description("Validate that a user can successfully create a contact by entering valid data ")
    public void createContact_withValidData_savesSuccessfully() {
        Contact contact = ContactDataGenerator.generateValidContact();

        ContactDetailsPage detailsPage = new ContactsListPage(driver)
                .tapAddContact()
                .fillAndSave(contact);

        Assert.assertTrue(detailsPage.isContactDisplayed(contact),
                "Saved contact name was not displayed on the contact details screen");

        ContactsListPage listPage = detailsPage.goBackToList();

        Assert.assertTrue(listPage.isContactDisplayed(contact),
                "Saved contact was not found in the contacts list after scrolling");
    }
}
