package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.getNavigationHelper().clickLink("groups");

        List<GroupData> before = app.getGroupHelper().getGroupsList();

        app.getGroupHelper().createGroup(new GroupData("testG", null, null));

        List<GroupData> after = app.getGroupHelper().getGroupsList();

        Assert.assertEquals(after.size(), before.size() + 1);
    }
}
