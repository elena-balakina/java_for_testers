package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.getNavigationHelper().clickLink("groups");

        List<GroupData> before = app.getGroupHelper().getGroupsList();

        GroupData group = new GroupData("test2", null, null);
        app.getGroupHelper().createGroup(group);

        List<GroupData> after = app.getGroupHelper().getGroupsList();

        Assert.assertEquals(after.size(), before.size() + 1);

        // компаратор - это интерфейс, который не имеет реализации, а только декларирует, объявляет
        // какие методы должны быть, но реализации не содержит
        //анонимная функция
        //Comparator<? super GroupData> byId = (o1, o2) -> Integer.compare(o1.getGroupId(), o2.getGroupId());
        // можно превратить список в поток

        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getGroupId(), g2.getGroupId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);
    }
}
