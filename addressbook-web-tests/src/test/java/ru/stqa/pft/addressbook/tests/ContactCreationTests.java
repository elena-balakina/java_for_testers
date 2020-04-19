package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/tiger.png");
        list.add(new Object[]{new ContactData().withFirstName("First Name 1").withLastName("Last Name 1").withAddress("Address 1").
                withHomePhone("home phone 1").withMobilePhone("mobile phone 1").withWorkPhone("work phone 1").
                withEmail("email 1").withEmail2("email2 1").withEmail3("email3 1").withGroup("test1").withPhoto(photo)});
        list.add(new Object[]{new ContactData().withFirstName("First Name 2").withLastName("Last Name 2").withAddress("Address 2").
                withHomePhone("home phone 2").withMobilePhone("mobile phone 2").withWorkPhone("work phone 2").
                withEmail("email 2").withEmail2("email2 2").withEmail3("email3 2").withGroup("test1").withPhoto(photo)});

        return list.iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) throws Exception {
        Contacts before = app.contact().all();

        app.contact().create(contact, true);

        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    }
}
