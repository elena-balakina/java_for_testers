package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
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

        ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "Roman", "Shutov", "Xeron", "+79701111122", "mail2@gmail.com", "test1");
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().clickLink("home");

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }
}
