package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletion() throws Exception {
        app.getNavigationHelper().clickLink("groups");

        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("testNEW", null, null));
        }

        List<GroupData> before = app.getGroupHelper().getGroupsList();

        app.getGroupHelper().selectGroup(before.size() - 1); //before-1 - удаляем самую нижнюю, последнюю в списке группу
        app.getGroupHelper().deleteSelectedGroups();
        app.getNavigationHelper().clickLink("groups");

        List<GroupData> after = app.getGroupHelper().getGroupsList();

        Assert.assertEquals(after.size(), before.size() - 1);

        // удаляем последнюю группу из массива before
        before.remove(before.size() - 1);

        // теперь у нас 2 одинаковых списка before и after
        // проверяем их совпадение (можно без цикла!)
        Assert.assertEquals(before, after);
    }
}
