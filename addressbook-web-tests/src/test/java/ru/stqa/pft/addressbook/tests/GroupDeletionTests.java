package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletion() throws Exception {
        app.clickLink("groups");
        app.selectGroup();
        app.deleteSelectedGroups();
        app.clickLink("groups");
    }
}
