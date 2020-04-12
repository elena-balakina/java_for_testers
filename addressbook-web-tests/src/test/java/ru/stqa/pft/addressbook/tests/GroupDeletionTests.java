package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().clickLink("groups");

        if (app.group().list().size() == 0) {
            app.group().create(new GroupData().withName("testNEW"));
        }
    }

    @Test
    public void testGroupDeletion() throws Exception {
        List<GroupData> before = app.group().list();

        int index = before.size() - 1;  //before-1 - удаляем самую нижнюю, последнюю в списке группу

        app.group().delete(index);

        List<GroupData> after = app.group().list();

        Assert.assertEquals(after.size(), before.size() - 1);

        // удаляем последнюю группу из массива before
        before.remove(index);

        // теперь у нас 2 одинаковых списка before и after
        // проверяем их совпадение (можно без цикла!)
        Assert.assertEquals(before, after);
    }
}
