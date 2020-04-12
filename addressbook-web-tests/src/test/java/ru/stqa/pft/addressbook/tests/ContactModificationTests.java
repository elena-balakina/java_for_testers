package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

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
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next(); // вернет любой элемент множества

        ContactData contact = new ContactData().withId(modifiedContact.getId()).
                withFirstName("Olga").withLastName("Lavova").withAddress("Xeron").
                withMobilePhone("+79701111122").withEmail("mail2@gmail.com").withGroup("test1");

        app.contact().modify(contact);

        Contacts after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
