package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider // данные генерируются прямо в коде
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

    @DataProvider // данные загружаются из файла CSV
    public Iterator<Object[]> validContactsFromCSV() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/tiger.png");

        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
        String line = reader.readLine();

        while (line != null) {
            String[] split = line.split(";");
            list.add(new Object[]{new ContactData().withFirstName(split[0]).withLastName(split[1]).withAddress(split[2]).
                    withHomePhone(split[3]).withMobilePhone(split[4]).withWorkPhone(split[5]).
                    withEmail(split[6]).withEmail2(split[7]).withEmail3(split[8]).withGroup(split[9]).withPhoto(photo)});
            line = reader.readLine();
        }

        return list.iterator();
    }

    @DataProvider // данные загружаются из файла XML
    public Iterator<Object[]> validContactFromXML() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
        String xml = "";
        String line = reader.readLine();

        while (line != null) {
            xml += line;
            line = reader.readLine();
        }

        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml); // читает данные из XML
        return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validContactFromXML")
    public void testContactCreation(ContactData contact) throws Exception {
        Contacts before = app.contact().all();

        app.contact().create(contact, true);

        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    }
}
