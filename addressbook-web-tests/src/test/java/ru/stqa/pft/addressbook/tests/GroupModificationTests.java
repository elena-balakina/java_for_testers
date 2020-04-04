package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().clickLink("groups");

        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("testNEW", null, null));
        }

        List<GroupData> before = app.getGroupHelper().getGroupsList();

        app.getGroupHelper().selectGroup(before.size() - 1); //before-1 - изменяем самую нижнюю, последнюю в списке группу
        app.getGroupHelper().initGroupModification();

        GroupData group = new GroupData(before.get(before.size() - 1).getGroupId(),"test1", "newHeader", "newFooter");

        app.getGroupHelper().fillGroupForm(group);
        app.getGroupHelper().submitGroupModification();
        app.getNavigationHelper().clickLink("groups");

        List<GroupData> after = app.getGroupHelper().getGroupsList();

        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(group);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }
}
