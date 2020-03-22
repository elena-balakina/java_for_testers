package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().clickLink("home");
        app.getContactHelperHelper().selectContact();
        app.getContactHelperHelper().deleteSelectedContact();
        app.driver.switchTo().alert().accept();
        app.getNavigationHelper().clickLink("home");
    }
}
