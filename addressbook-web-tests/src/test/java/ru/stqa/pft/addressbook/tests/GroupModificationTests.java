package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().clickLink("groups");

        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("testNEW"));
        }
    }

    @Test
    public void testGroupModification() {
        Set<GroupData> before = app.group().all();
        GroupData modifiedGroup = before.iterator().next(); // вернет любой элемент множества

        GroupData group = new GroupData().withId(modifiedGroup.getGroupId()).withName("testModified").
                withHeader("newHeader").withFooter("newFooter");

        app.group().modify(group);

        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedGroup);
        before.add(group);

        Assert.assertEquals(before, after);
    }
}
