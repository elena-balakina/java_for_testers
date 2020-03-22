package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.clickLink("add new");
        app.fillContactForm(new ContactData("Sidr", "Sidorov", "Novosibirsk", "+79159150000", "petr@gmail.com"));
        app.submitContactCreation();
        app.clickLink("home");
    }
}
