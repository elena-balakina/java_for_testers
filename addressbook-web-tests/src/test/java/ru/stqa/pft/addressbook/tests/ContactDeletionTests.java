package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().clickLink("home");

        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Kolya").
                    withLastName("Makov").withAddress("San Francisco").
                    withMobilePhone("+79701111111").withEmail("mail@gmail.com").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactDeletion() {
        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next(); // вернет любой элемент множества

        app.contact().delete(deletedContact);

        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedContact);
        Assert.assertEquals(before, after);
    }
}
