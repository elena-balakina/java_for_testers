package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().clickLink("home");

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Kolya", "Makov", "San Francisco", "+79701111111", "mail@gmail.com", "testG"), true);
        }

        int before = app.getContactHelper().getContactCount();

        app.getContactHelper().selectContact(before - 1);
        app.getContactHelper().deleteSelectedContact();
        app.driver.switchTo().alert().accept();
        app.getNavigationHelper().clickLink("home");

        int after = app.getContactHelper().getContactCount();

        Assert.assertEquals(after, before - 1);
    }
}
