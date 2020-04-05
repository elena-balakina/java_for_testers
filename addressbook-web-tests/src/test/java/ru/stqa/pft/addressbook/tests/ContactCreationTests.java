package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        int before = app.getContactHelper().getContactCount();


        ContactData contact = new ContactData("Sonya", "Esina", "New York", "+79701111122", "mail2@gmail.com", "test1");
        app.getContactHelper().createContact(contact, true);

        int after = app.getContactHelper().getContactCount();

        Assert.assertEquals(after, before + 1);
    }
}
