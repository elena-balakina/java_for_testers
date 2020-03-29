package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().clickLink("add new");
        app.getContactHelper().fillContactForm(new ContactData("Kolya", "Makov", "San Francisco", "+79701111111", "mail@gmail.com", "testF"), true);
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().clickLink("home");
    }
}
