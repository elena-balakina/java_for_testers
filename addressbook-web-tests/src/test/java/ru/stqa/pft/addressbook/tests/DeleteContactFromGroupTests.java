package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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
        GroupData selectedGroup = null;
        ContactData contactToDelete = null;

        // Перебираем все группы и ищем такую, в которой есть хотя бы 1 контакт, чтобы его удалить из группы
        while (isNull(selectedGroup)) {
            for (GroupData group : app.db().groups()) {
                if (group.getContacts().size() > 0) {
                    selectedGroup = group;
                    break;
                }
            }

            // если нет ни одной группы хотя бы с одним контактов, то берем любой контакт и добавляем его в любую группу
            if (isNull(selectedGroup)) {
                Contacts allContacts = app.db().contacts();
                Groups allGroups = app.db().groups();

                ContactData modifiedContact = allContacts.iterator().next(); // вернет любой контакт
                GroupData groupToAddTo = allGroups.iterator().next(); // вернет любую группу

                app.contact().addToGroup(modifiedContact);
                modifiedContact.inGroup(groupToAddTo);
            }
        }

        // ищем контакт, который добавлен в выбранную группу
        while (isNull(contactToDelete)) {
            for (ContactData contact : app.db().contacts()) {
                if (contact.getGroups().contains(selectedGroup)) {
                    contactToDelete = contact;
                    break;
                }
            }
        }

        System.out.println("Selected group: " + selectedGroup.getGroupName());
        System.out.println("Selected contact : " + contactToDelete.getFirstName() + " " +
                contactToDelete.getLastName());

        int numberOfContactsGroupsBefore = contactToDelete.getGroups().size();

        app.contact().deleteFromGroup(contactToDelete);
        contactToDelete.fromGroup(selectedGroup);

        int numberOfContactsGroupsAfter = contactToDelete.getGroups().size();

        Assert.assertEquals(numberOfContactsGroupsBefore, numberOfContactsGroupsAfter + 1);
    }

    private boolean isNull(ContactData contact) {
        return contact == null;
    }

    private boolean isNull(GroupData group) {
        return group == null;
    }
}
