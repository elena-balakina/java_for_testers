package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().clickLink("home");

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Kolya", "Makov", "San Francisco", "+79701111111", "mail@gmail.com", "testG"), true);
        }

        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Misha2", "Makov", "San Francisco", "+79701111111", "mail@gmail.com", "testF"), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().clickLink("home");


    }
}
