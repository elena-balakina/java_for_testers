package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletion() throws Exception {
        app.getNavigationHelper().clickLink("groups");

        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("testNEW", null, null));
        }

        int before = app.getGroupHelper().getGroupCount();

        app.getGroupHelper().selectGroup();
        app.getGroupHelper().deleteSelectedGroups();
        app.getNavigationHelper().clickLink("groups");

        int after = app.getGroupHelper().getGroupCount();

        Assert.assertEquals(after, before - 1);
    }
}
