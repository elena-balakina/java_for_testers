package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddContactToGroupTests extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        // проверяем наличие группы и если нет ни одной - создаем ее
        app.goTo().clickLink("groups");

        if (app.db().groups().size() == 0) {
            app.goTo().clickLink("groups");
            app.group().create(new GroupData().withName("testNEW"));
        }

        // проверяем наличие контакта и если нет ни одного, то создаем его
        app.goTo().clickLink("home");
        if (app.db().contacts().size() == 0) {
            app.goTo().clickLink("home");
            app.contact().create(new ContactData().withFirstName("Kolya").
                    withLastName("Makov").withAddress("Xeron").withHomePhone("212-20-50").
                    withMobilePhone("+79701111122").withWorkPhone("333-50-80").
                    withEmail("mail@gmail.com").withEmail2("mail2@gmail.com").withEmail3("mail3@gmail.com"), true);
        }
    }

    @Test
    public void testAddContactToGroup() {
        GroupData groupToAddTo = null;
        ContactData contactToAdd = null;

        // общее количество контактов в адресной книге
        Contacts allContacts = app.db().contacts();
        System.out.println("Total number of contacts: " + allContacts.size());

        // Перебираем все группы и ищем такую, которая содержит не все контакты
        while (isNull(groupToAddTo)) {
            for (GroupData group : app.db().groups()) {
                if (group.getContacts().size() < allContacts.size()) {
                    groupToAddTo = group;
                    break;
                }
            }

            if (isNull(groupToAddTo)) {
                app.goTo().clickLink("groups");
                app.group().create(new GroupData().withName("testNEW"));
            }
        }

        // ищем контакт, который не добавлен в выбранную группу
        while (isNull(contactToAdd)) {
            for (ContactData contact : app.db().contacts()) {
                if (!contact.getGroups().contains(groupToAddTo)) {
                    contactToAdd = contact;
                    break;
                }
            }
        }

        System.out.println("Selected group: " + groupToAddTo.getGroupName());
        System.out.println("Selected contact : " + contactToAdd.getFirstName() + " " + contactToAdd.getLastName());

        Groups before = contactToAdd.getGroups();
        System.out.println("Groups before: ");
        for (GroupData group : before) {
            System.out.println(group.getGroupName());
        }

        app.contact().addToGroup(contactToAdd, groupToAddTo);
        contactToAdd.inGroup(groupToAddTo);

        int id = contactToAdd.getId();

        ContactData contactAdded = null;
        // берем из БД добавленный контакт
        while (isNull(contactAdded)) {
            for (ContactData contact : app.db().contacts()) {
                if (contact.getId()==id) {
                    contactAdded = contact;
                    break;
                }
            }
        }

        Groups after = contactAdded.getGroups();
        System.out.println("Groups after: ");
        for (GroupData group : after) {
            System.out.println(group.getGroupName());
        }

        assertThat(after, equalTo(before.withAdded(groupToAddTo)));
    }

    private boolean isNull(ContactData contact) {
        return contact == null;
    }

    private boolean isNull(GroupData group) {
        return group == null;
    }
}
