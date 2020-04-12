package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

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
        Groups before = app.group().all();
        GroupData modifiedGroup = before.iterator().next(); // вернет любой элемент множества
        GroupData group = new GroupData().withId(modifiedGroup.getGroupId()).withName("testModified").
                withHeader("newHeader").withFooter("newFooter");

        app.group().modify(group);

        Groups after = app.group().all();

        assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    }
}
