package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteContactFromGroupTests extends TestBase {
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
        Contacts beforeContacts = app.db().contacts();
        Groups beforeGroups = app.db().groups();

        ContactData modifiedContact = beforeContacts.iterator().next(); // вернет любой контакт
        GroupData groupToRemoveFrom = beforeGroups.iterator().next(); // вернет любую группу

        // устанавливаем контакту группу, если она еще у него не установлена
        if (modifiedContact.getGroups().size() == 0) {
            app.contact().addToGroup(modifiedContact);
            modifiedContact.inGroup(groupToRemoveFrom);
        }

        app.contact().deleteFromGroup(modifiedContact);
        modifiedContact.fromGroup(groupToRemoveFrom);

        Assert.assertTrue(modifiedContact.getGroups().size() == 0);
    }
}
