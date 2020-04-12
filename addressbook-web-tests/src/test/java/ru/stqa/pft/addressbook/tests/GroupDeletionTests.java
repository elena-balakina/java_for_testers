package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().clickLink("groups");

        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("testNEW"));
        }
    }

    @Test
    public void testGroupDeletion() throws Exception {
        Set<GroupData> before = app.group().all();

        GroupData deletedGroup = before.iterator().next(); // вернет любой элемент множества

        app.group().delete(deletedGroup);

        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size() - 1);

        // удаляем последнюю группу из массива before
        before.remove(deletedGroup);

        // теперь у нас 2 одинаковых списка before и after
        // проверяем их совпадение (можно без цикла!)
        Assert.assertEquals(before, after);
    }
}
