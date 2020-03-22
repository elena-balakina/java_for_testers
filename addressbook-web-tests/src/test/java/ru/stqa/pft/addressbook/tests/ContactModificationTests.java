package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().clickLink("home");
        app.getContactHelperHelper().initContactModification();
        app.getContactHelperHelper().fillContactForm(new ContactData("Nina", "Abramova", "Omsk", "+79701111111", "mail@gmail.com"));
        app.getContactHelperHelper().submitContactModification();
        app.getNavigationHelper().clickLink("home");
    }
}
