package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().clickLink("home");

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Kolya", "Makov", "San Francisco", "+79701111111", "mail@gmail.com", "testG"), true);
        }

        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().initContactModification(before.size() - 1);
        app.getContactHelper().fillContactForm(new ContactData("Misha", "Mohov", "San Francisco", "+79701111111", "mail@gmail.com", "testF"), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().clickLink("home");

        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size());
    }
}
