package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().clickLink("home");
            app.contact().create(new ContactData().withFirstName("Kolya").
                    withLastName("Makov").withAddress("Xeron").withHomePhone("212-20-50").
                    withMobilePhone("+79701111122").withWorkPhone("333-50-80").
                    withEmail("mail@gmail.com").withEmail2("mail2@gmail.com").withEmail3("mail3@gmail.com"), true);
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next(); // вернет любой элемент множества

        ContactData contact = new ContactData().withId(modifiedContact.getId()).
                withFirstName("Olga").withLastName("Lavova").withAddress("Xeron").withHomePhone("212-20-50").
                withMobilePhone("+79701111122").withWorkPhone("444-44-44").withEmail("mail2@gmail.com").
                withEmail2("mail2@gmail.com").withEmail3("mail3@gmail.com");

        app.contact().modify(contact);

        Contacts after = app.db().contacts();
        Assert.assertEquals(after.size(), before.size());

        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));

        // проверка UI
        verifyContactListInUI();
    }
}
