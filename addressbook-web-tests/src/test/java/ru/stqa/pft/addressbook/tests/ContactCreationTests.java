package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        Contacts before = app.contact().all();

        File photo = new File("src/test/resources/tiger.png");

        ContactData contact = new ContactData().
                withFirstName("Lola").withLastName("Krasnova").withAddress("Saratov").withHomePhone("212-20-50").
                withMobilePhone("+79701111122").withWorkPhone("333-50-80").withEmail("mail@gmail.com").
                withEmail2("mail2@gmail.com").withEmail3("mail3@gmail.com").withGroup("test1").
                withPhoto(photo);

        app.contact().create(contact, true);

        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }
}
