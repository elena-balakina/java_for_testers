package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().clickLink("add new");
        app.getContactHelperHelper().fillContactForm(new ContactData("Maria", "Antonova", "Novosibirsk", "+79831111111", "mail@gmail.com"));
        app.getContactHelperHelper().submitContactCreation();
        app.getNavigationHelper().clickLink("home");
    }
}
