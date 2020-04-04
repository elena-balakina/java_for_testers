package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.getNavigationHelper().clickLink("groups");

        List<GroupData> before = app.getGroupHelper().getGroupsList();

        GroupData group = new GroupData("testG", null, null);
        app.getGroupHelper().createGroup(group);

        List<GroupData> after = app.getGroupHelper().getGroupsList();

        Assert.assertEquals(after.size(), before.size() + 1);

        int max = 0;
        for (GroupData g: after){
            if (g.getGroupId() >max){
                max = g.getGroupId();
            }
        }

        group.setGroupId(max);

        before.add(group);

        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }
}