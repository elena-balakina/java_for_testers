package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        Set<ContactData> before = app.contact().all();

        ContactData contact = new ContactData().
                withFirstName("Uryi").withLastName("Velesov").withAddress("Saratov").
                withMobilePhone("+79701111122").withEmail("mail2@gmail.com").withGroup("test1");

        app.contact().create(contact, true);

        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());

        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
