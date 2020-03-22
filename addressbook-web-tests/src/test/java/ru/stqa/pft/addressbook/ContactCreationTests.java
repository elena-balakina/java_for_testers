package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        clickLink("add new");
        fillContactForm(new ContactData("Sidr", "Sidorov", "Novosibirsk", "+79159150000", "petr@gmail.com"));
        submitContactCreation();
        clickLink("home");
    }
}
