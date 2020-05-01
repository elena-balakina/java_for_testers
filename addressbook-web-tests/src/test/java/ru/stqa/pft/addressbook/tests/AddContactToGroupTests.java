package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.security.acl.Group;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        ContactData modifiedContact = null;
        GroupData groupToAddTo = null;

        // перебираем контакты и ищем первый вариант контакт + группа, в которую его можно добавить
        while (isNull(modifiedContact)) {
            for (ContactData contact : app.db().contacts()) {
                for (GroupData group : app.db().groups()) {

                    for (ContactData contactInGroup : group.getContacts()) {
                        if (contactInGroup == contact) {
                            break;
                        }
                    }

                    groupToAddTo = group;
                }
                modifiedContact = contact;
            }

            if (isNull(groupToAddTo)) {
                app.goTo().clickLink("groups");
                app.group().create(new GroupData().withName("testNEW"));
            }

            if (isNull(modifiedContact)) {
                app.goTo().clickLink("home");
                app.contact().create(new ContactData().withFirstName("Kolya").
                        withLastName("Makov").withAddress("Xeron").withHomePhone("212-20-50").
                        withMobilePhone("+79701111122").withWorkPhone("333-50-80").
                        withEmail("mail@gmail.com").withEmail2("mail2@gmail.com").withEmail3("mail3@gmail.com"), true);
            }
        }

        app.contact().addToGroup(modifiedContact);
        modifiedContact.inGroup(groupToAddTo);

        Assert.assertTrue(modifiedContact.getGroups().size() > 0);


        /////////////////////////////
        /*
        // перебираем в БД все контакты и если находим такой, который не добавлен ни в какую группу,
        // то берем его за modifiedContact
        ContactData modifiedContact = null;

        while (isNull(modifiedContact)) {
            for (ContactData contact : app.db().contacts()) {
                if (contact.getGroups().size() == 0) {
                    modifiedContact = contact;
                    break;
                }
            }

            // создаем новый контакт, который не добавлен ни в одну группу
            if (isNull(modifiedContact)) {
                app.goTo().clickLink("home");
                app.contact().create(new ContactData().withFirstName("Kolya").
                        withLastName("Makov").withAddress("Xeron").withHomePhone("212-20-50").
                        withMobilePhone("+79701111122").withWorkPhone("333-50-80").
                        withEmail("mail@gmail.com").withEmail2("mail2@gmail.com").withEmail3("mail3@gmail.com"), true);
            }
        }


        Groups beforeGroups = app.db().groups();
        GroupData groupToAddTo = beforeGroups.iterator().next(); // вернет группу, в которую будем добавлять (любую)

        app.contact().addToGroup(modifiedContact);
        modifiedContact.inGroup(groupToAddTo);

        Assert.assertTrue(modifiedContact.getGroups().size() > 0);

        //Contacts after = app.db().contacts();
        //Assert.assertEquals(after.size(), beforeContacts.size());

        //assertThat(after, equalTo(beforeContacts.without(modifiedContact).withAdded(modifiedContact)));

        // проверка UI
        //verifyContactListInUI();*/
    }

    private boolean isNull(ContactData contact) {
        return contact == null;
    }

    private boolean isNull(GroupData group) {
        return group == null;
    }
}
