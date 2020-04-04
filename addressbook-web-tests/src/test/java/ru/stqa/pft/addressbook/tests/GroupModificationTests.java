package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().clickLink("groups");

        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("testNEW", null, null));
        }

        int before = app.getGroupHelper().getGroupCount();

        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData("testG", "newHeader", "newFooter"));
        app.getGroupHelper().submitGroupModification();
        app.getNavigationHelper().clickLink("groups");

        int after = app.getGroupHelper().getGroupCount();

        Assert.assertEquals(after, before);

    }
}
